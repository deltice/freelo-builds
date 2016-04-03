'''
Created on Apr 1, 2016

@author: Charlie Hou
'''
import requests
import statistics

def requestMatchData(region, matchID):
   
    URL = "https://na.api.pvp.net/api/lol/" + region + "/v2.2/match/" + matchID + "?includeTimeline=true&api_key=81e718d7-036d-4c3a-9bb5-257f8890cd46"
    response = requests.get(URL)
    return response.json()
    
def requestSeedData(number):
    URL = "https://s3-us-west-1.amazonaws.com/riot-api/seed_data/matches" + str(number) + ".json"
    response = requests.get(URL)
    return response.json()
#finds the list of items a team has at each time    
def itemListsTeamOne(matchFrames):
    #the list that will contain lists of items at each time
    itemlists = [] 
    #list that keeps a running total of the items a team has
    littlelist = []    
    #t is the time, j is the events that have passed
    
    for t in range (len(matchFrames)):
        
        #skipping the first timestamp, which has no events
        if matchFrames[t]["timestamp"] > 0:
            #list of items at one time
            
            for j in range (len(matchFrames[t]["events"])):
                #searches for an event that is an item purchase, and on the team specified
                if matchFrames[t]["events"][j]["eventType"] == "ITEM_PURCHASED" and matchFrames[t]["events"][j]["participantId"] <= 5:
                    #puts that item into the one-time list of items
                    littlelist.append(matchFrames[t]["events"][j]["itemId"])
                elif matchFrames[t]["events"][j]["eventType"] == "ITEM_DESTROYED" and matchFrames[t]["events"][j]["participantId"] <= 5:
                    #takes out the item that was destroyed in the running total
                    index = -1
                    for i in range(len(littlelist)):
                        if int(matchFrames[t]["events"][j]["itemId"]) == int(littlelist[i]):
                            index = i
                            break
                    #takes into account items that are consumed in base
                    if index != -1:
                        del littlelist[index]
                    else:
                        continue
                else:
                    continue    
            #puts that list into the item   
            littlelistcopy = list(littlelist)    
            itemlists.append(littlelistcopy)
    
    return itemlists
#for team two
def itemListsTeamTwo(matchFrames):
    #the list that will contain lists of items at each time
    itemlists = [] 
    #list that keeps a running total of the items a team has
    littlelist = []    
    #t is the time, j is the events that have passed
    
    for t in range (len(matchFrames)):
        
        #skipping the first timestamp, which has no events
        if matchFrames[t]["timestamp"] > 0:
            #list of items at one time
            
            for j in range (len(matchFrames[t]["events"])):
                #searches for an event that is an item purchase, and on the team specified
                if matchFrames[t]["events"][j]["eventType"] == "ITEM_PURCHASED" and matchFrames[t]["events"][j]["participantId"] >= 6:
                    #puts that item into the one-time list of items
                    littlelist.append(matchFrames[t]["events"][j]["itemId"])
                elif matchFrames[t]["events"][j]["eventType"] == "ITEM_DESTROYED" and matchFrames[t]["events"][j]["participantId"] >= 6:
                    #takes out the item that was destroyed in the running total
                    index = -1
                    for i in range(len(littlelist)):
                        if int(matchFrames[t]["events"][j]["itemId"]) == int(littlelist[i]):
                            index = i
                            break
                    #consumed in base items that are not in inventory
                    if index != -1:
                        del littlelist[index]
                    else:
                        continue
                else:
                    continue   
            #puts that list into the item   
            littlelistcopy = list(littlelist)    
            itemlists.append(littlelistcopy)
    
    return itemlists


#takes an item timeline, stattype to give timeline of a stat

#gives back a 1-d list of the total stats per time for a team
def statSum(matchFrames, stattype, team):

#finds it for the other team, because situation is based on what the other team builds
    itemlists = itemListsTeamTwo(matchFrames)
    
    if team == 2:
        itemlists = itemListsTeamOne(matchFrames)
        
    sumlist = []
    statlist = statRetrieval()
    
    for i in range(len(itemlists)):
        sum = 0.0
        for j in range(len(itemlists[i])):
            #try catch for the case if the stattype doesn't exist for that item
            try:
                sum = sum + statlist[str(itemlists[i][j])]["stats"][stattype]
            except KeyError:
                sum = sum + 0
        sumlist.append(sum)
    return sumlist        
#item stat retrieval    
def statRetrieval():
    URL = "https://global.api.pvp.net/api/lol/static-data/na/v1.2/item?itemListData=stats&api_key=81e718d7-036d-4c3a-9bb5-257f8890cd46"
    response = requests.get(URL)
    itemJSON = response.json()
    return itemJSON["data"]

def distribution():
    ADlist = []
    APlist = []
    ARMlist = []
    MRlist = []

    #loop through seed data 
    for k in range (1, 2):
        seed = requestSeedData(k)
        #loop through the number of matches in each seed
        for i in range (len(seed["matches"])):
                
            #find the sum of stats from each match
            matchframes = seed["matches"][i]["timeline"]["frames"]
            #AD stuff
            singleGamelistAD = list(statSum(matchframes, "FlatPhysicalDamageMod"))
            singleGamelistCRIT = list(statSum(matchframes, "FlatCritChanceMod"))
            singleGamelistAS = list(statSum(matchframes, "PercentAttackSpeedMod"))
            
            #AP stuff
            singleGamelistAP = list(statSum(matchframes, "FlatMagicDamageMod"))
            singleGamelistMP = list(statSum(matchframes, ""))
            
            #tank stuff
            singleGamelistHP = list(statSum(matchframes, "FlatHPPoolMod"))
            singleGamelistARM = list(statSum(matchframes, "FlatArmorMod"))
            singleGamelistMR = list(statSum(matchframes, "FlatSpellBlockMod"))
            
    
            for j in range (len(singleGamelistAD)):
                ADlist.append(singleGamelistAD[j] + singleGamelistAD[j] * singleGamelistAS[j] + singleGamelistAD[j] * singleGamelistCRIT[j])
                APlist.append(singleGamelistAP[j])  
                ARMlist.append(singleGamelistARM[j] + singleGamelistHP[j]) 
                MRlist.append(singleGamelistMR[j] + singleGamelistHP[j])

                
    #the stats
    print(statistics.mean(ADlist))
    print(statistics.stdev(ADlist))
    print(statistics.mean(APlist))
    print(statistics.stdev(APlist))
    print(statistics.mean(ARMlist))
    print(statistics.stdev(ARMlist))
    print(statistics.mean(MRlist))
    print(statistics.stdev(MRlist))

             
def main():
    distribution()
    
if __name__ == '__main__':
    main()