
//
//  BLEDefinitions.m
//  BLEDemo
//
//  Created by ProfileMyRun on 1/18/12.
//  Copyright (c) 2012 connectBlue. All rights reserved.
//

#import "BLEDefinitions.h"

//const unsigned char genericAccessServiceUuid[SERVICE_UUID_DEFAULT_LEN]      = {0x18, 0x00};
//const unsigned char genericAttributeServiceUuid[SERVICE_UUID_DEFAULT_LEN]   = {0x18, 0x01};
const unsigned char immediateAlertServiceUuid[SERVICE_UUID_DEFAULT_LEN]     = {0x18, 0x02};
const unsigned char linkLossServiceUuid[SERVICE_UUID_DEFAULT_LEN]           = {0x18, 0x03};
const unsigned char txPowerServiceUuid[SERVICE_UUID_DEFAULT_LEN]            = {0x18, 0x04};
const unsigned char currentTimeServiceUuid[SERVICE_UUID_DEFAULT_LEN]        = {0x18, 0x05};
const unsigned char refTimeUpdateServiceUuid[SERVICE_UUID_DEFAULT_LEN]      = {0x18, 0x06};
const unsigned char nextDstChangeServiceUuid[SERVICE_UUID_DEFAULT_LEN]      = {0x18, 0x07};
const unsigned char glucoseServiceUuid[SERVICE_UUID_DEFAULT_LEN]            = {0x18, 0x08};
const unsigned char healthThermServiceUuid[SERVICE_UUID_DEFAULT_LEN]        = {0x18, 0x09};
const unsigned char deviceIdServiceUuid[SERVICE_UUID_DEFAULT_LEN]           = {0x18, 0x0a};
const unsigned char networkAvailServiceUuid[SERVICE_UUID_DEFAULT_LEN]       = {0x18, 0x0b};
//const unsigned char xxxServiceUuid[SERVICE_UUID_DEFAULT_LEN]                = {0x18, 0x0c};
const unsigned char heartRateServiceUuid[SERVICE_UUID_DEFAULT_LEN]          = {0x18, 0x0d};
const unsigned char phoneAlertStatusServiceUuid[SERVICE_UUID_DEFAULT_LEN]   = {0x18, 0x0e};
const unsigned char batteryServiceUuid[SERVICE_UUID_DEFAULT_LEN]            = {0x18, 0x0f};
const unsigned char bloodPressureServiceUuid[SERVICE_UUID_DEFAULT_LEN]      = {0x18, 0x10};
const unsigned char alertNotificationServiceUuid[SERVICE_UUID_DEFAULT_LEN]  = {0x18, 0x11};
const unsigned char humanIntDeviceServiceUuid[SERVICE_UUID_DEFAULT_LEN]     = {0x18, 0x12};
const unsigned char scanParametersServiceUuid[SERVICE_UUID_DEFAULT_LEN]     = {0x18, 0x13};
const unsigned char runSpeedCadenceServiceUuid[SERVICE_UUID_DEFAULT_LEN]    = {0x18, 0x14};
const unsigned char aForePressureServiceUuid[SERVICE_UUID_DEFAULT_LEN]        = {0x18, 0x15};
const unsigned char aHeelPressureServiceUuid[SERVICE_UUID_DEFAULT_LEN]        = {0x18, 0x16};



const unsigned char accServiceUuid[SERVICE_UUID_DEFAULT_LEN]       = {0xff, 0xa0};
const unsigned char tempServiceUuid[SERVICE_UUID_DEFAULT_LEN]      = {0xff, 0xe0};
const unsigned char ledServiceUuid[SERVICE_UUID_DEFAULT_LEN]       = {0xff, 0xd0};

const unsigned char   serialPortServiceUuid[SERIAL_PORT_SERVICE_UUID_LEN] = {
    0x24, 0x56, 0xe1, 0xb9, 0x26, 0xe2, 0x8f, 0x83,
    0xe7, 0x44, 0xf3, 0x4f, 0x01, 0xe9, 0xd7, 0x01};


const unsigned char batteryLevelCharactUuid[CHARACT_UUID_DEFAULT_LEN]       = {0x2a, 0x19};
const unsigned char systemIdCharactUuid[CHARACT_UUID_DEFAULT_LEN]           = {0x2a, 0x23};
const unsigned char modelNumberCharactUuid[CHARACT_UUID_DEFAULT_LEN]        = {0x2a, 0x24};
const unsigned char serialNumberCharactUuid[CHARACT_UUID_DEFAULT_LEN]       = {0x2a, 0x25};
const unsigned char firmwareRevisionCharactUuid[CHARACT_UUID_DEFAULT_LEN]   = {0x2a, 0x26};
const unsigned char hardwareRevisionCharactUuid[CHARACT_UUID_DEFAULT_LEN]   = {0x2a, 0x27};
const unsigned char swRevisionCharactUuid[CHARACT_UUID_DEFAULT_LEN]         = {0x2a, 0x28};
const unsigned char manufactNameCharactUuid[CHARACT_UUID_DEFAULT_LEN]       = {0x2a, 0x29};
const unsigned char regCertCharactUuid[CHARACT_UUID_DEFAULT_LEN]            = {0x2a, 0x2a};
const unsigned char aForePressureLevelCharactUuid[CHARACT_UUID_DEFAULT_LEN]   = {0x2a, 0x2b};
const unsigned char aHeelPressureLevelCharactUuid[CHARACT_UUID_DEFAULT_LEN]   = {0x2a, 0x2c};

const unsigned char accRangeCharactUuid[CHARACT_UUID_DEFAULT_LEN]           = {0xff, 0xa2};
const unsigned char accXCharactUuid[CHARACT_UUID_DEFAULT_LEN]               = {0xff, 0xa3};
const unsigned char accYCharactUuid[CHARACT_UUID_DEFAULT_LEN]               = {0xff, 0xa4};
const unsigned char accZCharactUuid[CHARACT_UUID_DEFAULT_LEN]               = {0xff, 0xa5};

const unsigned char tempValueCharactUuid[CHARACT_UUID_DEFAULT_LEN]          = {0xff, 0xe1};

const unsigned char redLedCharactUuid[CHARACT_UUID_DEFAULT_LEN]             = {0xff, 0xd1};
const unsigned char greenLedCharactUuid[CHARACT_UUID_DEFAULT_LEN]           = {0xff, 0xd2};

const unsigned char flowControlModeCharactUuid[CHARACT_UUID_SERIAL_LEN] = {
    0x24, 0x56, 0xe1, 0xb9, 0x26, 0xe2, 0x8f, 0x83,
    0xe7, 0x44, 0xf3, 0x4f, 0x01, 0xe9, 0xd7, 0x02};

const unsigned char serialPortFifoCharactUuid[CHARACT_UUID_SERIAL_LEN] = {
    0x24, 0x56, 0xe1, 0xb9, 0x26, 0xe2, 0x8f, 0x83,
    0xe7, 0x44, 0xf3, 0x4f, 0x01, 0xe9, 0xd7, 0x03};

const unsigned char creditsCharactUuid[CHARACT_UUID_SERIAL_LEN] = {
    0x24, 0x56, 0xe1, 0xb9, 0x26, 0xe2, 0x8f, 0x83,
    0xe7, 0x44, 0xf3, 0x4f, 0x01, 0xe9, 0xd7, 0x04};
