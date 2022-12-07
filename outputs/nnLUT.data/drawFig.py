import matplotlib.pyplot as plt
import random
import os
from collections import defaultdict

errors = defaultdict(lambda: [])

for filename in os.listdir("/Users/lingershen/UBC/CPEN502/CPEN502_Project/outputs/nnLUT.data/"):
    if filename.startswith("nn_"):
        with open(filename, 'r') as f:
            fl = f.readlines()
            v = float(fl[1])
            for line in fl:
                s = line.strip("\n").split(":")
                if len(s) == 2:
                    errors[v].append(float(s[1]))

plt.figure(1)
for a in errors.keys():
    plt.plot(errors[a], label = a, linewidth = 0.5)
plt.xlabel('Epoch')
plt.ylabel('RMS')
plt.legend()
plt.show()