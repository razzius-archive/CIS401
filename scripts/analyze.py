from __future__ import division

import sys
import numpy as np


def main(sents, receiveds):
    """Create a histogram of the delays between sent and received."""
    results = {}
    total_sent = 0
    total_received = 0

    for s in sents:
        with open(s) as f:
            for line in f.readlines():
                packet_id = '_'.join(line.split()[1:])
                microseconds = int(line.split()[0])
                results[packet_id] = {'sent': microseconds}
                total_sent += 1

    for r in receiveds:
        with open(r) as f:
            for line in f.readlines():
                packet_id = '_'.join(line.split()[1:])
                microseconds = int(line.split()[0])
                try:
                    results[packet_id]['received'] = microseconds
                    total_received += 1
                except KeyError:
                    print('bad key "{}"'.format(packet_id))


    ms = [(p['received'] - p['sent']) for (k, p) in results.iteritems() if 'received' in p and 'razzi' in k]
    global_min = min([p['sent'] for p in results.itervalues()])
    global_max = max([p['sent'] for p in results.itervalues()])
    mn = min(ms)
    mx = max(ms)

    alive = len([x for x in ms if x < 10000]) / len(ms)
    std = np.std(ms)
    mean = np.mean(ms)

    packet_per = total_received / total_sent

    trial_time = (global_max - global_min) / 1e6


    packets_second = total_received / trial_time

    print('Mean: {}. min: {}. max: {}. std: {}. alive: {}'.format(mean, mn, mx, std, alive))
    print('global_min: {}. global_max: {}. total received: {}. packets_drop_%: {}. packets_second {}'.format(global_min, global_max, total_received, 100 * (1 - packet_per), packets_second))
    print('trial time {}'.format(trial_time))

    # __import__('ipdb').set_trace()
    # ms = filter(lambda m: m < 51000, ms)
    ms = map(lambda m: m / 1000, ms)

    # __import__('ipdb').set_trace()
    # plt.hist(ms, alpha=.5, bins=100, normed=True)
    # plt.xlabel('Response Time (ms)')
    # plt.ylabel('Fraction of Data Processed')
    # plt.title('(3 VMs)')
    # axes = plt.gca()
    # axes.set_ylim([0, 1])
    # axes.set_xlim([0, 10])
    # plt.legend()
    # plt.show()

if __name__ == '__main__':
    num_args = len(sys.argv) - 1

    sents = sys.argv[1:len(sys.argv)//2 + 1]
    receiveds = sys.argv[len(sys.argv)//2 + 1:]
    main(sents, receiveds)
