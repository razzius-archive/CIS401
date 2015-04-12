import pexpect
import sys
import re

from config import XEN_CONFIG_PATH, IMG_NAME, CONFIG_NAME

def log_event(analytics_endpoint, process):
    command = 'curl --data "action=bootvm" http://{}'.format(analytics_endpoint)
    process.sendline(command)
    for line in process:
        print line

def get_ip(process):
    process.setecho(False)
    process.sendline('ifconfig eth0')
    for line in process:
        ip_match = re.search('inet addr:((?:\d{1,3}\.){3}\d{1,3})', line)
        if ip_match:
            return ip_match.group(1)

def main():
    vm_name = sys.argv[1]
    memory = sys.argv[2]
    analytics_endpoint = sys.argv[3]

    img_path = '{}/{}'.format(XEN_CONFIG_PATH, IMG_NAME)
    config_path = '{}/{}'.format(XEN_CONFIG_PATH, CONFIG_NAME)

    xen_options=[
        'name="{}"'.format(vm_name),
        'memory={}'.format(memory),
        'disk=["file:{},xvda,w"]'.format(img_path),
    ]

    boot_process = pexpect.spawn(
        '/usr/local/sbin/xl',
        ['create', '-c', config_path] + xen_options,
        timeout=300)
    boot_process.expect_exact('Will boot')
    boot_process.sendline('\n')
    boot_process.expect_exact('login:')
    boot_process.sendline('root')
    boot_process.expect_exact('Password:')
    boot_process.sendline('password')
    boot_process.expect_exact('root@localhost:~#')
    ip_address = get_ip(boot_process)
    log_event(analytics_endpoint, boot_process)
    print(ip_address)

if __name__ == '__main__':
    if len(sys.argv) < 4:
        print('Usage: boot_vm.py NAME MEMORY ANALYTICS_ENDPOINT')
        exit(1)
    else:
        main()
