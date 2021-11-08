import json
with open('mmts.json') as f:
  data = json.load(f)
# Output: {'name': 'Bob', 'languages': ['English', 'Fench']}
#print(data["evaluated_individuals"])
for result in data["evaluated_individuals"]:
    fuel = (result["operation_values"]["fuel"])
    maxfuel = (result["operation_values"]["maxFuel"])
    minfuel = (result["operation_values"]["minFuel"])
    tar1 = (result["operation_values"]["target1"])
    tar2 = (result["operation_values"]["target2"])
    tar3 = (result["operation_values"]["target3"])
    tar4 = (result["operation_values"]["target4"])
    print("Scenario------------")
    print(fuel)
    print(maxfuel)
    print(minfuel)
    print(tar1)
    print(tar2)
    print(tar3)
    print(tar4)
    print("Failed TestCase-----")
    if fuel< minfuel:
        print("belowMinfuel")
    if fuel> maxfuel:
        print("aboveMaxfuel")
    if tar1==False or tar2==False or tar3==False or tar4==False:
        print("collectAllTarget")    
    else:
        if tar1==False:
            print("collect1stTarget")
        elif tar2==False:
            print("collect2ndTarget")  
        elif tar3==False:
            print("collect3rdTarget")
        elif tar4==False:
            print("collect4thTarget")
        else:
            print("golden")
