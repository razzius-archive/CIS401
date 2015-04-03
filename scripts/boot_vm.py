import pexpect
import sys

from config import XEN_CONFIG_PATH, IMG_NAME, CONFIG_NAME


def main():
    vm_name = sys.argv[1]
    memory = sys.argv[2]

    img_path = '{}/{}'.format(XEN_CONFIG_PATH, IMG_NAME)
    config_path = '{}/{}'.format(XEN_CONFIG_PATH, CONFIG_NAME)

    xen_options=[
        'name="{}"'.format(vm_name),
        'memory={}'.format(memory),
        'disk=["file:{},xvda,w"]'.format(img_path),
    ]

    boot_process = pexpect.spawn('/usr/local/sbin/xl', ['create', '-c', config_path] + xen_options)
    __import__('ipdb').set_trace()

if __name__ == '__main__':
    if len(sys.argv) < 3:
        print('Usage: boot_vm.py NAME MEMORY')
        exit(1)
    else:
        main()
