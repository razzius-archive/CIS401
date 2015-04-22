import signal
from pox.core import core
import pox.openflow.libopenflow_01 as of
import datetime

mac_to_port = {}

def info(signum, frame):
    print(mac_to_port)

log = core.getLogger()
signal.signal(signal.SIGINT, info)

class Controller (object):
    def __init__ (self, connection):
        self.connection = connection
        connection.addListeners(self)
        self.mac_to_port = {}

    def resend_packet (self, packet_in, out_port):
        msg = of.ofp_packet_out()
        msg.data = packet_in
        action = of.ofp_action_output(port = out_port)
        msg.actions.append(action)
        self.connection.send(msg)

    def _handle_PacketIn (self, event):
        packet = event.parsed
        mac_to_port[str(packet.src)] = event.port
       
 
        if str(packet.dst) not in mac_to_port:
            msg = of.ofp_packet_out()
            msg.actions.append(of.ofp_action_output(port = of.OFPP_FLOOD))
            msg.buffer_id = event.ofp.buffer_id
            msg.in_port = event.port
            self.connection.send(msg)
        else:
            outport = mac_to_port[str(packet.dst)]
            self.resend_packet(packet, outport)            
            try:
                from_ip = event.parsed.next.srcip
                to_ip = event.parsed.next.dstip
                if from_ip is not None and to_ip is not None:
                    print("Detected packet: src = {}, dst = {}".format(from_ip, to_ip))
                    print("src(MAC) = {}, dst(MAC) = {}".format(packet.src, packet.dst))
            except AttributeError as e:
                pass

def launch():
    def start_switch (event):
        log.debug("Controlling %s" % (event.connection,))
        Controller(event.connection)
    core.openflow.addListenerByName("ConnectionUp", start_switch)
