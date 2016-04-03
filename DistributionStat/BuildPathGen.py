'''
Created on Apr 2, 2016

@author: Charlie Hou
'''
import StatValues
import SituationalBuild
from StatValues import statSum
import copy
import sys
import sqlite3

#the hash    
def hash(AD, AP, ARM, MR, champid):
    #calculated from normalizing each sum; sample means and stdevs were calculated in StatValues 
    #in an independent run
    adcomp = ((AD - 440)/492 + 5) * 1000
    apcomp = ((AP - 176)/174 + 5) * 100
    armcomp = ((ARM - 1500)/1332 + 5) * 10
    mrcomp = ((MR - 1462)/1285 + 5)
    return adcomp + apcomp + armcomp + mrcomp + 10000 * champid

#build order
def finalbuildorder(match, id):
    item0 = match["participants"][id]["stats"]["item0"]
    item1 = match["participants"][id]["stats"]["item1"]
    item2 = match["participants"][id]["stats"]["item2"]
    item3 = match["participants"][id]["stats"]["item3"]
    item4 = match["participants"][id]["stats"]["item4"]
    item5 = match["participants"][id]["stats"]["item5"]
    return str(item0) + "-" + str(item1) +"-" + str(item2) + "-" +str(item3) + "-" +str(item4) +"-"+ str(item5)

#taking build list and putting it into two dimensional list indexed by situation
def situationalbuildlist(buildList):
    #the 2d array to be returned
    sitbuild = []
    
    #signifying 2000 different categories of hashes
    for i in range (0,2000):
        littlelist = []
        for j in range (len(buildList)):
            
            #if it is in range, put the build in
            if buildList[j].getsituation() >= i * 5000 and buildList[j].getsituation() <= (i+1) * 5000:
                littlelist.append(buildList[j])
            
        sitbuild.append(list(littlelist))
    return sitbuild

#key for the sorted method
def getKey(buildpath):
    return buildpath.getwinrate()

#putting the same build paths together into one object so that winrates actually have meaning, not only 1 or 0
def condensebuilds(list):
    for i in range(len(list)):
        for j in range(len(list[i])):
            build1 = list[i][j]
            
            #looks through the column for the same build
            for k in range(j + 1, len(list[j])):
                build2 = list[i][k]
                
                #adds the wins and losses
                if build1.getbuildseq() == build2.getbuildseq():
                    win = build2.getwins()
                    loss = build2.getlosses()
                    build1.addWin(win)
                    build2.addloss(loss)
                    
                    #get rid of the extra build
                    del list[i][k]
          
                
def main():
    buildlist = []
    #loopthrough the files
    for k in range (1):
        seedJSON = StatValues.requestSeedData(1)
        for i in range(len(seedJSON["matches"])):
            match = seedJSON["matches"][i]
            matchframes = match["timeline"]["frames"]
            
            
            #loop through each participant
            for j in range (1,10):
                team = 0
                if j < 6:
                    team == 1
                else:
                    team == 2
                #sums of individual stats    
                singleGamelistAD = statSum(matchframes, "FlatPhysicalDamageMod", team)
                singleGamelistHP = statSum(matchframes, "FlatHPPoolMod", team)
                singleGamelistAP = statSum(matchframes, "FlatMagicDamageMod", team)
                singleGamelistARM = statSum(matchframes, "FlatArmorMod", team)
                singleGamelistCRIT = statSum(matchframes, "FlatCritChanceMod", team)
                singleGamelistMR = statSum(matchframes, "FlatSpellBlockMod", team)
                singleGamelistAS = statSum(matchframes, "PercentAttackSpeedMod", team)
                #loop through the timeline sums
                for t in range (len(singleGamelistAD)):
                    
                    #aggregates stats into "effective stats"
                    champid = match["participants"][j]["championId"]
                    AD = singleGamelistAD[t] + singleGamelistAD[t] * singleGamelistAS[t] + singleGamelistAD[t] * singleGamelistCRIT[t]
                    AP = singleGamelistAP[t]
                    ARM = singleGamelistARM[t] + singleGamelistHP[t]
                    MR = singleGamelistMR[t] + singleGamelistHP[t]
                    
                    #creates the hash for that "situation"
                    hashsit = hash(AD, AP, ARM, MR, champid)
                    
                    #build object, which has build and situation in it as well as winrate
                    build = SituationalBuild.SituationalBuild(hashsit, finalbuildorder(match, j))
                    buildlist.append(build)
                    if match["participants"][j]["stats"]["winner"] == True:
                        build.addWin()
                    else:
                        build.addloss() 

    #list now is sorted by situation and by winrate
    '''
    for i in range (len(buildlist)):
        print(buildlist[i].getbuildseq() + " " + str(buildlist[i].getwinrate()) + " " + str(buildlist[i].getsituation()))
    '''
    #processes the builds
    situationbuild = situationalbuildlist(buildlist)
    for k in range (len(situationbuild)):
        sorted(situationbuild[k], key = getKey)    
    
    condensebuilds(situationbuild)

   #database stuff
    conn = sqlite3.connect('main.db')
    

    conn.execute('''CREATE TABLE DATA2
        (Hash            INT
       r0              TEXT,
       r1              TEXT,
       r2              TEXT,
       r3              TEXT,
       r4              TEXT,
       r5              TEXT,
       r6              TEXT,
       r7              TEXT,
       r8              TEXT,
       r9              TEXT,
       r10             TEXT,
       r11             TEXT,
       r12             TEXT,
       r13             TEXT,
       r14             TEXT,
       r15             TEXT,
       r16             TEXT,
       r17             TEXT,
       r18             TEXT,
       r19             TEXT,
       r20             TEXT,
       r21             TEXT,
       r22             TEXT,
       r23             TEXT,
       r24             TEXT,
       r25             TEXT,
       r26             TEXT,
       r27             TEXT,
       r28             TEXT,
       r29             TEXT,
       r30             TEXT,
       r31             TEXT,
       r32             TEXT,
       r33             TEXT,
       r34             TEXT,
       r35             TEXT,
       r36             TEXT,
       r37             TEXT,
       r38             TEXT,
       r39             TEXT,
       r40             TEXT,
       r41             TEXT,
       r42             TEXT,
       r43             TEXT,
       r44             TEXT,
       r45             TEXT,
       r46             TEXT,
       r47             TEXT,
       r48             TEXT,
       r49             TEXT,
       r50             TEXT);''') 
    
    #basically writes something like a 2d table, one dimension the hash and the other builds sorted by winrate 
    for i in range (len(situationbuild)):
        for j in range (len(situationbuild[i])):
            if j > 50:
                break
            if j < 1:
                continue
            conn.execute("INSERT INTO DATA2 (Hash, " +"r" + str(j) + ") \
              VALUES (" + str(int(situationbuild[i][j].getsituation()/5000)) + ", " + str(situationbuild[i][j].getbuildseq()) + " )");
    conn.commit()
    conn.close()       
if __name__ == '__main__':
    main()