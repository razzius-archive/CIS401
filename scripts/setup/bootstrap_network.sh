sh start_ovs.sh
ovs-vsctl add-br ovsbr0
ovs-vsctl add-port ovsbr0 eth0
python set_interfaces.py
/etc/init.d/networking restart
ifconfig eth0 0.0.0.0
