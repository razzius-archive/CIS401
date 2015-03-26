# assume eth0 is running and has ip address

import subprocess
import re

interfaces = """
auto lo
iface lo inet loopback

auto ovsbr0
allow-ovs ovsbr0
iface ovsbr0 inet static
	ovs_type OVSBridge
	ovs_ports eth0
	address {}
	gateway {}
	netmask {}
	broadcast {}
	up ip link set ovsbr0 up
	down ip link set ovsbr0 down
""".strip()

ifconfig = subprocess.check_output(['ifconfig', 'ovsbr0'])
ipv4_re = r'(\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3})'

address_re = r"""
inet addr:{0}\s+Bcast:{0}\s+Mask:{0}
""".format(ipv4_re).strip()

ip_address_matches = re.findall(address_re, ifconfig)
if (ip_address_matches):
	address, broadcast, netmask = ip_address_matches[0]
else:
	print('Error: no IP address found in eth0')


gateway = None

route_info = subprocess.check_output(['route', '-n'])
for line in route_info.split('\n'):
	columns = line.split()
	if len(columns) > 1 and columns[1] != '0.0.0.0':
		gateway = columns[1]

if gateway is None:
	print('Error: no gateway found in eth0')

with open('/etc/network/interfaces') as f:
	f.write(interfaces.format(address, gateway, netmask, broadcast))
