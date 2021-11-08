# -*- coding: utf-8 -*-
"""

This is a simple example test executable script for use with Enki.

Notes: Every executable script requires a class to be defined that is inherited from the EnkiExecutable class. This
class must include three things:
    1.) An operation_specification property that returns a dictionary of values for Enki to explore.
    2.) A behavior_specification property that returns a dictionary of values for Enki to diversify.
    3.) A execute function that takes a dictionary of values that matches operation_specification and returns a
    dictionary of values that matches behavior_specification. The purpose of this function is to evaluate any given
    system within a selected set of operational values and monitor its behavior under the corresponding condition.
"""


import numpy as np
from random import randint
from enki.core.interface import EnkiExecutable


class TestExecutable(EnkiExecutable):
    """This is a simple example test executable for use with Enki. It allows Enki to search a space of possible
    (x, y) values, evaluate the distance of each selected point from the origin (0, 0), and diversify selected points
    based on the their distance from the origin.

    """
    @property
    def operation_specification(self):
        return {
            'fuel': [0, 100],
            'maxFuel': [0, 100], 
            'minFuel': [0, 100],
            'enemyPos': [1,8], 	
            'target1': [True,False], 
            'target2': [True,False], 
            'target3': [True,False], 
            'target4': [True,False], 
           # 'priorityPath': [1,8], 
        }

    @property
    def behavior_specification(self):
        return {
            'canMove':[True, False],
            'nextMove':[1, 8],				
	
        }

    def execute(self, operation_values):
        # get operation values:
        fuel = operation_values['fuel']
        maxFuel = operation_values['maxFuel'] 
        minFuel = operation_values['minFuel'] 
        enemyPos = operation_values['enemyPos'] 
        target1 = operation_values['target1'] 
        target2 = operation_values['target2'] 
        target3 = operation_values['target3'] 
        target4 = operation_values['target4'] 		
       # priorityPath = operation_values['priorityPath']

        nextMove =  1 if enemyPos!=1 else randint(2, 8)        
        canMove =  True if fuel<=maxFuel and fuel>=minFuel and nextMove!=0 else False	
        # pack behavior values
        behavior_values = {
            'canMove': canMove,	
            'nextMove': nextMove,			
        }

        # return behavior values
        return behavior_values
