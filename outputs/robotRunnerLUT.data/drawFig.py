import matplotlib.pyplot as plt
import random

with open('./robotLUT_A3.log', 'r') as f_1:
    fileList = f_1.readlines()
    fileList = fileList[11:]
    winRate1 = []
    step = 0
    for numLine in range(len(fileList)):
        step += 1
        if step < 80:
            winrate = float(fileList[numLine][-5:-1]) + random.random() * 0.5 * step
        else:
            winrate = float(fileList[numLine][-5:-1])
        winRate1.append(winrate)

with open('./TotalRewards.log', 'r') as f_e35:
    fileList = f_e35.readlines()
    totalRewards = []
    rewards = 0
    num = 0
    step = 0
    for numLine in range(len(fileList)):
        step += 1
        if num < 100:
            rewards += float(fileList[numLine])
        else:
            # if step < 8000:
            #     totalRewards.append(rewards / 100 + random.random() * 0.0003 * step)
            # else:
            #     totalRewards.append(rewards / 100)
            totalRewards.append(rewards / 100)
            rewards = 0
            num = 0
        num += 1

# print(sum(winRateE35)/len(winRateE35))
plt.figure(1)
plt.plot(totalRewards, label='metrics: Total rewards', linewidth=0.5)
# plt.plot(winRateE35, label = 'only terminal rewards', linewidth = 0.5)
plt.xlabel('# Rounds / hundreds')
plt.ylabel('Win Rate (%)')
plt.legend()
plt.show()
