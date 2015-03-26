from subprocess import Popen
import sys

from config import XEN_CONFIG_PATH, IMG_NAME, CONFIG_NAME


def main():
    vm_name = sys.argv[1]
    memory = sys.argv[2]

    img_path = "{}/{}".format(XEN_CONFIG_PATH, IMG_NAME)
    config_path = "{}/{}".format(XEN_CONFIG_PATH, CONFIG_NAME)

    xen_options="""
        name="{}"
        memory={}
        disk=["file:{},xvda,w"]
        """.format(vm_name, memory, img_path)

    Popen("xl create -c {} {}"
        .format(config_path, xen_options)
        .split()
    )

if __name__ == '__main__':
    if len(sys.argv) < 3:
        print('Usage: boot_vm.py NAME MEMORY')
        exit(1)
    else:
        main()
