from subprocess import Popen
import sys

from .config import XEN_CONFIG_HOME, IMG_PATH


def main():
    vm_name = sys.argv[1]
    memory = sys.argv[2]

    xen_options="""
        name="{}"
        memory={}
        disk=["file:{},xvda,w"]
        """.format(vm_name, memory, IMG_PATH)

    Popen("xl create -c {}/default.cfg {}"
        .format(XEN_CONFIG_HOME, xen_options)
    )

if __name__ == '__main__':
    if len(sys.argv) < 3:
        print('Usage: boot_vm.py NAME MEMORY')
        exit(1)
    else:
        main()
