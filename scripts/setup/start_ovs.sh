rm /usr/local/etc/openvswitch/conf.db
rm /usr/local/etc/openvswitch/.conf.db.~lock~
modprobe openvswitch
ovsdb-tool create /usr/local/etc/openvswitch/conf.db vswitch.ovsschema
ovsdb-server --remote=punix:/usr/local/var/run/openvswitch/db.sock --remote=db:Open_vSwitch,Open_vSwitch,manager_options --pidfile --detach
ovs-vsctl --no-wait init
ovs-vswitchd --pidfile --detach
