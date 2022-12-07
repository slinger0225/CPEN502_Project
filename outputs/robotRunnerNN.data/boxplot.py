import matplotlib.pyplot as plt
import numpy as np

data = []
with open ("./robotNN.txt", 'r') as file:
    lines = file.readlines()
    for line in lines:
        ele = line.split(",")
        if len(ele) > 2:
            data.append(float(ele[5]))
            print(data)
fig = plt.figure(figsize =(10, 7))
 
# Creating plot
plt.boxplot(data)
 
# show plot
plt.show()