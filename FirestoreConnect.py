import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
import numpy as np


def connect_Firebase():
    # Firestore access
    # Use a service account.
    if not firebase_admin._apps:
        cred = credentials.Certificate('serviceAccount.json')
        default_app = firebase_admin.initialize_app(cred)
    db = firestore.client()
    return db


#turn string inputs into acceptable 0-1 values for model
def FixInput(Device):
    Device = dict(Device)
    #switch statement for different values of fields
    Switcher = {
        "bluejay-1.2-8893284": 1,
        "": 0,
        "true": 1,
        "false": 0,
        "METERED": 1,
        "NOT_METERED": 0,
        14: 1,
        13: 0.75,
        12: 0.5,
        11: 0.25,
        10: 0,
        "2023-01-01": 0,
        "2023-06-01": 0.5,
        "2024-01-01": 1,
        "Rooted": 0,
        "NotRooted": 1,
        "TRUSTED": 1,
        "NOT_TRUSTED": 0
    }
    # fix bootloader
    newVal = Switcher.get(Device.get("bootLoader"))
    if newVal == None:
        newVal = 0
    Device.update({"bootLoader": newVal})

    # fix encryption
    newVal = Switcher.get(Device.get("encryption"))
    if newVal == None:
        newVal = 0
    Device.update({"encryption": newVal})

    # fix meteredNetwork
    newVal = Switcher.get(Device.get("meteredNetwork"))
    if newVal == None:
        newVal = 0
    Device.update({"meteredNetwork": newVal})

    # fix osVersion
    newVal = Switcher.get(Device.get("osVersion"))
    if newVal == None:
        newVal = 0
    Device.update({"osVersion": newVal})

    # fix patch
    newVal = Switcher.get(Device.get("patch"))
    if newVal == None:
        newVal = 0
    Device.update({"patch": newVal})

    # fix root
    newVal = Switcher.get(Device.get("root"))
    if newVal == None:
        newVal = 0
    Device.update({"root": newVal})

    # fix trustedNetwork
    newVal = Switcher.get(Device.get("trustedNetwork"))
    if newVal == None:
        newVal = 0
    Device.update({"trustedNetwork": newVal})

    return Device


def FixOutput(Device):
    Device = dict(Device)
    for i in range(10):
        appNum = i + 1
        for j in range(10):
            permNum = j + 1
            # Fix_existsScore and _grantedScore for every App and permission.
            field1 = f"App{appNum}Perm{permNum}_existsScore"
            field2 = f"App{appNum}Perm{permNum}_grantedScore"
            if (Device.get(field1) == 5):
                Device.update({field1: 1})
            if (Device.get(field2) == 5):
                Device.update({field2: 1})
    Switcher = {
        2: 1,
        1: 0.5,
        0: 0
    }
    # Fix osScore
    newVal = Switcher.get(Device.get("osScore"))
    if newVal == None:
        newVal = 0
    Device.update({"osScore": newVal})
    # Fix patchScore
    newVal = Switcher.get(Device.get("patchScore"))
    if newVal == None:
        newVal = 0
    Device.update({"patchScore": newVal})

    return Device

#turn string inputs into acceptable 0-1 values for model
def FixInput2(Device):
    Device = dict(Device)
    #switch statement for different values of fields
    Switcher = {
        "bluejay-1.2-8893284": 1,
        "": 0,
        "true": 1,
        "false": 0,
        "METERED": 1,
        "NOT_METERED": 0,
        14: 1,
        13: 0.75,
        12: 0.5,
        11: 0.25,
        10: 0,
        "2024-01-01": 0,
        "2023-09-05": 0.5,
        "2024-03-01": 1,
        "Rooted": 0,
        "NotRooted": 1,
        "TRUSTED": 1,
        "NOT_TRUSTED": 0
    }
    # fix bootloader
    newVal = Switcher.get(Device.get("bootLoader"))
    if newVal == None:
        newVal = 0
    Device.update({"bootLoader": newVal})

    # fix encryption
    newVal = Switcher.get(Device.get("encryption"))
    if newVal == None:
        newVal = 0
    Device.update({"encryption": newVal})

    # fix meteredNetwork
    newVal = Switcher.get(Device.get("meteredNetwork"))
    if newVal == None:
        newVal = 0
    Device.update({"meteredNetwork": newVal})

    # fix osVersion
    newVal = Switcher.get(Device.get("osVersion"))
    if newVal == None:
        newVal = 0
    Device.update({"osVersion": newVal})

    # fix patch
    newVal = Switcher.get(Device.get("patch"))
    if newVal == None:
        newVal = 0
    Device.update({"patch": newVal})

    # fix root
    newVal = Switcher.get(Device.get("root"))
    if newVal == None:
        newVal = 0
    Device.update({"root": newVal})

    # fix trustedNetwork
    newVal = Switcher.get(Device.get("trustedNetwork"))
    if newVal == None:
        newVal = 0
    Device.update({"trustedNetwork": newVal})

    return Device


def FixOutput2(Device):
    Device = dict(Device)
    for i in range(10):
        appNum = i + 1
        for j in range(10):
            permNum = j + 1
            # Fix_existsScore and _grantedScore for every App and permission.
            field1 = f"App{appNum}Perm{permNum}_existsScore"
            field2 = f"App{appNum}Perm{permNum}_grantedScore"
            if (Device.get(field1) == 10):
                Device.update({field1: 1})
            if (Device.get(field2) == 10):
                Device.update({field2: 1})
            if (Device.get(field1) == 5):
                Device.update({field1: 0.5})
            if (Device.get(field2) == 5):
                Device.update({field2: 0.5})
    Switcher = {
        2: 1,
        1: 0.5,
        0: 0
    }
    # Fix osScore
    newVal = Switcher.get(Device.get("osScore"))
    if newVal == None:
        newVal = 0
    Device.update({"osScore": newVal})
    # Fix patchScore
    newVal = Switcher.get(Device.get("patchScore"))
    if newVal == None:
        newVal = 0
    Device.update({"patchScore": newVal})

    return Device

#turn string inputs into acceptable 0-1 values for model
def FixInput3(Device):
    Device = dict(Device)
    #switch statement for different values of fields
    Switcher = {
        "bluejay-1.2-8893284": 1,
        "": 0,
        "true": 1,
        "false": 0,
        "METERED": 1,
        "NOT_METERED": 0,
        14: 1,
        13: 0.75,
        12: 0.5,
        11: 0.25,
        10: 0,
        "2024-01-01": 0,
        "2023-09-05": 0.5,
        "2024-03-01": 1,
        "Rooted": 0,
        "NotRooted": 1,
        "TRUSTED": 1,
        "NOT_TRUSTED": 0
    }
    # fix bootloader
    newVal = Switcher.get(Device.get("bootLoader"))
    if newVal == None:
        newVal = 0
    Device.update({"bootLoader": newVal})

    # fix encryption
    newVal = Switcher.get(Device.get("encryption"))
    if newVal == None:
        newVal = 0
    Device.update({"encryption": newVal})

    # fix meteredNetwork
    newVal = Switcher.get(Device.get("meteredNetwork"))
    if newVal == None:
        newVal = 0
    Device.update({"meteredNetwork": newVal})

    # fix osVersion
    newVal = Switcher.get(Device.get("osVersion"))
    if newVal == None:
        newVal = 0
    Device.update({"osVersion": newVal})

    # fix patch
    newVal = Switcher.get(Device.get("patch"))
    if newVal == None:
        newVal = 0
    Device.update({"patch": newVal})

    # fix root
    newVal = Switcher.get(Device.get("root"))
    if newVal == None:
        newVal = 0
    Device.update({"root": newVal})

    # fix trustedNetwork
    newVal = Switcher.get(Device.get("trustedNetwork"))
    if newVal == None:
        newVal = 0
    Device.update({"trustedNetwork": newVal})

    return Device


def FixOutput3(Device):
    Device = dict(Device)
    for i in range(10):
        appNum = i + 1
        for j in range(10):
            permNum = j + 1
            # Fix_existsScore and _grantedScore for every App and permission.
            field1 = f"App{appNum}Perm{permNum}_existsScore"
            field2 = f"App{appNum}Perm{permNum}_grantedScore"
            if (Device.get(field1) == 5):
                Device.update({field1: 1})
            if (Device.get(field2) == 5):
                Device.update({field2: 1})
    Switcher = {
        4: 1,
        3: 0.75,
        2: 0.50,
        1: 0.25,
        0: 0
    }
    # Fix osScore
    newVal = Switcher.get(Device.get("osScore"))
    if newVal == None:
        newVal = 0
    Device.update({"osScore": newVal})
    Switcher2 = {
        2: 1,
        1: 0.5,
        0: 0
    }
    # Fix patchScore
    newVal = Switcher2.get(Device.get("patchScore"))
    if newVal == None:
        newVal = 0
    Device.update({"patchScore": newVal})

    return Device

#turn string inputs into acceptable 0-1 values for model
def FixInput4(Device):
    Device = dict(Device)
    #switch statement for different values of fields
    Switcher = {
        "bluejay-1.2-8893284": 1,
        "bluejay-1.1-8893284": 0.5,
        "": 0,
        "true": 1,
        "false": 0,
        "METERED": 1,
        "NOT_METERED": 0,
        14: 1,
        13: 0.75,
        12: 0.5,
        11: 0.25,
        10: 0,
        "2024-01-01": 0,
        "2023-09-05": 0.5,
        "2024-03-01": 1,
        "Rooted": 0,
        "NotRooted": 1,
        "TRUSTED": 1,
        "NOT_TRUSTED": 0
    }
    # fix bootloader
    newVal = Switcher.get(Device.get("bootLoader"))
    if newVal == None:
        newVal = 0
    Device.update({"bootLoader": newVal})

    # fix encryption
    newVal = Switcher.get(Device.get("encryption"))
    if newVal == None:
        newVal = 0
    Device.update({"encryption": newVal})

    # fix meteredNetwork
    newVal = Switcher.get(Device.get("meteredNetwork"))
    if newVal == None:
        newVal = 0
    Device.update({"meteredNetwork": newVal})

    # fix osVersion
    newVal = Switcher.get(Device.get("osVersion"))
    if newVal == None:
        newVal = 0
    Device.update({"osVersion": newVal})

    # fix patch
    newVal = Switcher.get(Device.get("patch"))
    if newVal == None:
        newVal = 0
    Device.update({"patch": newVal})

    # fix root
    newVal = Switcher.get(Device.get("root"))
    if newVal == None:
        newVal = 0
    Device.update({"root": newVal})

    # fix trustedNetwork
    newVal = Switcher.get(Device.get("trustedNetwork"))
    if newVal == None:
        newVal = 0
    Device.update({"trustedNetwork": newVal})

    return Device


def FixOutput4(Device):
    Device = dict(Device)
    for i in range(10):
        appNum = i + 1
        for j in range(10):
            permNum = j + 1
            # Fix_existsScore and _grantedScore for every App and permission.
            field1 = f"App{appNum}Perm{permNum}_existsScore"
            field2 = f"App{appNum}Perm{permNum}_grantedScore"
            if (Device.get(field1) == 5):
                Device.update({field1: 1})
            if (Device.get(field2) == 5):
                Device.update({field2: 1})
    Switcher = {
        2: 1,
        1: 0.5,
        0: 0
    }
    # Fix osScore
    newVal = Switcher.get(Device.get("osScore"))
    if newVal == None:
        newVal = 0
    Device.update({"osScore": newVal})
    # Fix patchScore
    newVal = Switcher.get(Device.get("patchScore"))
    if newVal == None:
        newVal = 0
    Device.update({"patchScore": newVal})

    return Device


def getTrainingInputs(db,set_number):
    collectionName=""
    match(set_number):
        case 1:
            collectionName="trainingInputs"
        case 2:
            collectionName="trainingInputs2"
        case 3:
            collectionName="trainingInputs3"
        case 4:
            collectionName="trainingInputs4"
    # Getting Training Inputs
    # TrainInputdocs = db.collection("trainingInputs").limit(10).stream()
    TrainInputdocs = db.collection(collectionName).stream()
    temp = []
    tempdict = dict()
    TrainingInputs = []
    for doc in TrainInputdocs:
        # print(f"\n{doc._data}")
        # print(f"{doc.id} => {doc.to_dict()}")
        tempdict = dict(sorted(doc.to_dict().items()))
        match(set_number):
            case 1:
                tempdict = FixInput(tempdict)
            case 2:
                tempdict = FixInput2(tempdict)
            case 3:
                tempdict = FixInput3(tempdict)
            case 4:
                tempdict = FixInput4(tempdict)
        temp = list(tempdict.values())
        TrainingInputs.append(temp)
    trainingInputArray = np.array(TrainingInputs)
    return trainingInputArray


def getTrainingOutputs(db,set_number):
    collectionName=""
    match(set_number):
        case 1:
            collectionName="trainingOutputs"
        case 2:
            collectionName="trainingOutputs2"
        case 3:
            collectionName="trainingOutputs3"
        case 4:
            collectionName="trainingOutputs4"
    # Getting Training Outputs
    temp = []
    tempdict = dict()
    TrainingOutputs = []
    # TrainOutputdocs = db.collection("trainingOutputs").limit(10).stream()
    TrainOutputdocs = db.collection(collectionName).stream()
    for doc in TrainOutputdocs:
        # print(f"\n{doc._data}")
        # print(f"{doc.id} => {doc.to_dict()}")
        tempdict = dict(sorted(doc.to_dict().items()))
        match(set_number):
            case 1:
                tempdict = FixOutput(tempdict)
            case 2:
                tempdict = FixOutput2(tempdict)
            case 3:
                tempdict = FixOutput3(tempdict)
            case 4:
                tempdict = FixOutput4(tempdict)
        temp = list(tempdict.values())
        TrainingOutputs.append(temp)
    trainingOutputArray = np.array(TrainingOutputs)
    return trainingOutputArray


def getTestingInputs(db,set_number):
    collectionName=""
    match(set_number):
        case 1:
            collectionName="testingInputs"
        case 2:
            collectionName="testingInputs2"
        case 3:
            collectionName="testingInputs3"
        case 4:
            collectionName="testingInputs4"
    # Getting Testing Inputs
    temp = []
    tempdict = dict()
    TestingInputs = []
    # TestInputdocs = db.collection("testingInputs").limit(5).stream()
    TestInputdocs = db.collection(collectionName).stream()
    for doc in TestInputdocs:
        # print(f"\n{doc._data}")
        # print(f"{doc.id} => {doc.to_dict()}")
        tempdict = dict(sorted(doc.to_dict().items()))
        match(set_number):
            case 1:
                tempdict = FixInput(tempdict)
            case 2:
                tempdict = FixInput2(tempdict)
            case 3:
                tempdict = FixInput3(tempdict)
            case 4:
                tempdict = FixInput4(tempdict)
        temp = list(tempdict.values())
        TestingInputs.append(temp)
    testingInputArray = np.array(TestingInputs)
    return testingInputArray


def getTestingOutputs(db,set_number):
    collectionName=""
    match(set_number):
        case 1:
            collectionName="testingOutputs"
        case 2:
            collectionName="testingOutputs2"
        case 3:
            collectionName="testingOutputs3"
        case 4:
            collectionName="testingOutputs4"
    # Getting Testing Outputs
    temp = []
    tempdict = dict()
    TestingOutputs = []
    # TestOutputdocs = db.collection("testingOutputs").limit(5).stream()
    TestOutputdocs = db.collection(collectionName).stream()

    for doc in TestOutputdocs:
        # print(f"\n{doc._data}")
        # print(f"{doc.id} => {doc.to_dict()}")
        tempdict = dict(sorted(doc.to_dict().items()))
        match(set_number):
            case 1:
                tempdict = FixOutput(tempdict)
            case 2:
                tempdict = FixOutput2(tempdict)
            case 3:
                tempdict = FixOutput3(tempdict)
            case 4:
                tempdict = FixOutput4(tempdict)
        temp = list(tempdict.values())
        TestingOutputs.append(temp)
    testingOutputArray = np.array(TestingOutputs)
    return testingOutputArray
