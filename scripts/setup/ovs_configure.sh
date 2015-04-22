# Functions to get IP address, gateway, netmask, and broadcast of eth0
getIPAddr() {
    ifconfig eth0 | grep 'inet addr:'| cut -d: -f2 | awk '{ print $1}'    
}
getGateway() {
    route -n | grep 'UG[ \t]' | awk '{print $2}'
}
getNetmask() {
    ifconfig eth0 | awk '/Mask:/{ print $4;} ' | cut -c6-
}
getBroadcast() {
    ifconfig eth0 | awk '/Bcast:/{ print $3;} ' | cut -c7-
}

ipAddr=$(getIPAddr)
gateway=$(getGateway)
netmask=$(getNetmask)
broadcast=$(getBroadcast)

# Start OVS
modprobe openvswitch
rm /usr/local/etc/openvswitch/conf.db
ovsdb-tool create /usr/local/etc/openvswitch/conf.db vswitchd/vswitch.ovsschema
ovsdb-server --remote=punix:/usr/local/var/run/openvswitch/db.sock --remote=db:Open_vSwitch,Open_vSwitch,manager_options --pidfile --detach
ovs-vsctl --no-wait init
ovs-vswitchd --pidfile --detach

# Setup bridge and controller
ovs-vsctl add-br ovsbr0
ovs-vsctl add-port ovsbr0 eth0
ovs-vsctl set-controller ovsbr0 tcp:$ipAddr:6633
ifconfig eth0 0

# Modify /etc/network/interfaces file
cat > /etc/network/interfaces <<EOF
auto lo
iface lo inet loopback

auto ovsbr0
allow-ovs ovsbr0
iface ovsbr0 inet static
    ovs_type OVSBridge
    ovs_ports eth0
    address $ipAddr
    gateway $gateway 
    netmask $netmask
    broadcast $broadcast
    up ip link set ovsbr0 up
    down ip link set ovsbr0 down
    dns-nameservers 8.8.8.8 8.8.4.4
EOF
/etc/init.d/networking restart

# Start controller
#python ../controller/pox.py ../controller/controller
