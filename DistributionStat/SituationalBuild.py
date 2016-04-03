'''
Created on Apr 2, 2016

@author: YuChing
'''

class SituationalBuild(object):
    
    __situation = 0
    __buildSequence = ""
    __wins = 0.0
    __losses = 0.0
    
    #a situational build has a situation and a build 
    #sequence
    def __init__(self, situation, buildSequence):
        self.__situation = situation
        self.__buildSequence = buildSequence
    
    def addWin(self, number = None):
        if number is None:
            self.__wins = self.__wins + 1.0
        else:
            self.__wins = self.__wins + number    
    def addloss(self, number = None):
        if number is None:
            self.__losses = self.__losses + 1.0
        else:
            self.__wins = self.__wins + number
        
    def getwinrate(self):
        return self.__wins/(self.__wins + self.__losses)
    def getsituation(self):
        return self.__situation
    def getbuildseq(self):
        return self.__buildSequence
    def getwins(self):
        return self.__wins
    def getlosses(self):
        return self.__losses
    def __repr__(self):
        winrate = self.__wins/(self.__wins + self.__losses)
        return '{}'.format(self.__buildSequence, winrate)