import matplotlib.pyplot as plt

with open('outputs/robotRunnerLUT.data/robotLUT_e=0.35.log', 'r') as f_e35:
    fileList = f_e35.readlines()
    fileList = fileList[9:]
    winRateE35 = []
    for numLine in range(len(fileList)):
        winRateE35.append(float(fileList[numLine][-5:-1]))
        
with open('outputs/robotRunnerLUT.data/CrimsonTyphoon.log', 'r') as f_:
    fileList = f_.readlines()
    fileList = fileList[9:]
    winRate_typhoon = []
    for numLine in range(len(fileList)):
        winRate_typhoon.append(float(fileList[numLine][-5:-1]))

print(sum(winRateE35)/len(winRateE35))  
plt.figure(1)
plt.plot(winRateE35, label = 'e = 0.35', linewidth = 0.5)
plt.xlabel('# Rounds / hundreds')
plt.ylabel('Win Rate (%)')
plt.legend()
plt.show()

plt.figure(2)
plt.plot(winRate_typhoon, 'b', label = 'e = 0.35', linewidth = 0.5)
plt.xlabel('# Rounds / hundreds')
plt.ylabel('Win Rate Typhoon(%)')
plt.legend()
plt.show()