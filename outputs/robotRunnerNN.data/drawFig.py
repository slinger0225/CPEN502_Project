import matplotlib.pyplot as plt

with open('./robotNN.log', 'r') as f_1:
    fileList = f_1.readlines()
    fileList = fileList[11:]
    winRate1 = []
    for numLine in range(len(fileList)):
        winRate1.append(float(fileList[numLine][-5:-1]))

# with open('./robotLUT_terminal.log', 'r') as f_e35:
#     fileList = f_e35.readlines()
#     fileList = fileList[9:]
#     winRateE35 = []
#     for numLine in range(len(fileList)):
#         winRateE35.append(float(fileList[numLine][-5:-1]))

#print(sum(winRateE35)/len(winRateE35))
plt.figure(1)
plt.plot(winRate1, label = 'fuck!', linewidth = 0.5)
#plt.plot(winRateE35, label = 'only terminal rewards', linewidth = 0.5)
plt.xlabel('# Rounds / hundreds')
plt.ylabel('Win Rate (%)')
plt.legend()
plt.show()