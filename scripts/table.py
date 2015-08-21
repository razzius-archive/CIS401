import sys
from datetime import datetime

import matplotlib.pyplot as plt


def main(sent, received):
    """Create a histogram of the delays between sent and received."""
    results = {}
    hists = []

    for sent, received in zip(sent, received):
        with open(sent) as f:
            for line in f.readlines():
                packet_id = line.split()[-1]
                log_time = ' '.join(line.split()[:2])
                timestamp = datetime.strptime(log_time, '%Y-%m-%d %H:%M:%S,%f')
                results[packet_id] = {'sent': timestamp}

        with open(received) as f:
            for line in f.readlines():
                packet_id = line.split()[-1]
                log_time = ' '.join(line.split()[:2])
                timestamp = datetime.strptime(log_time, '%Y-%m-%d %H:%M:%S,%f')
                results[packet_id]['received'] = timestamp

        ms = [(p['received'] - p['sent']).microseconds for p in results.itervalues() if 'received' in p]
        # ms = filter(lambda m: m < 51000, ms)
        ms = map(lambda m: m / 1000, ms)
        hists.append(ms)

    for i, h in enumerate(hists):
        if i == 0:
            label = '1 VM'
        else:
            label = '{} VMs'.format(i + 1)

        plt.hist(h, alpha=.5, bins=1000, normed=True)
    plt.xlabel('Response Time (ms)')
    plt.ylabel('Fraction of Data Processed')
    # plt.title('(3 VMs)')
    axes = plt.gca()
    # axes.set_ylim([0, 1])
    axes.set_xlim([0, 20])
    plt.legend()
    plt.show()

if __name__ == '__main__':
    num_args = len(sys.argv) - 1
    if num_args < 2 or num_args % 2:
        print('Usage: histogram SENT ... RECEIVED ... (must be even)')
        exit(1)

    sents = sys.argv[1:1 + num_args / 2]
    receiveds = sys.argv[1 + num_args / 2:]
    main(sents, receiveds)
