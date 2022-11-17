import matplotlib.pyplot as plt

with open('outputs/robotRunnerLUT.data/robotLUT_e=0.log', 'r') as f_e35:
    fileList = f_e35.readlines()
    fileList = fileList[9:]
    winRateE0 = []
    for numLine in range(len(fileList)):
        winRateE0.append(float(fileList[numLine][-5:-1]))

with open('outputs/robotRunnerLUT.data/robotLUT_e=0.35.log', 'r') as f_e35:
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
plt.plot(winRateE35, "r", label = 'e = 0.35', linewidth = 0.5)
plt.plot(winRateE45, "b", label = 'e = 0.45', linewidth = 0.5)
plt.xlabel('# Rounds / hundreds')
plt.ylabel('Win Rate (%)')
plt.legend()
plt.show()