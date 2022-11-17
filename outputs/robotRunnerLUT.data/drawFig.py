import matplotlib.pyplot as plt

with open('./robotLUT_offpolicy.log', 'r') as f_1:
    fileList = f_1.readlines()
    fileList = fileList[9:]
    winRate1 = []
    for numLine in range(len(fileList)):
        winRate1.append(float(fileList[numLine][-5:-1]))

with open('./robotLUT_terminal.log', 'r') as f_e35:
    fileList = f_e35.readlines()
    fileList = fileList[9:]
    winRateE35 = []
    for numLine in range(len(fileList)):
        winRateE35.append(float(fileList[numLine][-5:-1]))

with open('outputs/robotRunnerLUT.data/robotLUT_e=0.15.log', 'r') as f_e35:
    fileList = f_e35.readlines()
    fileList = fileList[9:]
    winRateE15 = []
    for numLine in range(len(fileList)):
        winRateE15.append(float(fileList[numLine][-5:-1]))

with open('outputs/robotRunnerLUT.data/robotLUT_e=0.45.log', 'r') as f_e35:
    fileList = f_e35.readlines()
    fileList = fileList[9:]
    winRateE45 = []
    for numLine in range(len(fileList)):
        winRateE45.append(float(fileList[numLine][-5:-1]))

plt.figure(1)
plt.plot(winRate1, label = 'intermediate rewards', linewidth = 0.5)
plt.plot(winRateE35, label = 'only terminal rewards', linewidth = 0.5)
plt.xlabel('# Rounds / hundreds')
plt.ylabel('Win Rate (%)')
plt.legend()
plt.show()