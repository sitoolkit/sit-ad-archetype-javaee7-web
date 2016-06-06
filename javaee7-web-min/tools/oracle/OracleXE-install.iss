****************************************************************************************
**                                                                                    **
**  Response file to perform silent install of Oracle Database 11g Express Edition    **
**                                                                                    **
**  Values for the following variables are configurable:                              **
**  szDir - Provide a valid path                                                      **
**  TNSPort - Provide any valid available port number                                 **
**  MTSPort - Provide any valid available port number                                 **
**  HTTPPort - Provide any valid available port number                                **
**  SYSPassword - Provide a valid password string                                     **
**                                                                                    **
****************************************************************************************

[{05A7B662-80A3-4EB9-AE1D-89A62449431C}-DlgOrder]
Dlg0={05A7B662-80A3-4EB9-AE1D-89A62449431C}-SdWelcome-0
Count=7
Dlg1={05A7B662-80A3-4EB9-AE1D-89A62449431C}-SdLicense2Rtf-0
Dlg2={05A7B662-80A3-4EB9-AE1D-89A62449431C}-SdComponentDialog-0
Dlg3={05A7B662-80A3-4EB9-AE1D-89A62449431C}-AskPort-13013
Dlg4={05A7B662-80A3-4EB9-AE1D-89A62449431C}-AskSYSPassword-13011
Dlg5={05A7B662-80A3-4EB9-AE1D-89A62449431C}-SdStartCopy-0
Dlg6={05A7B662-80A3-4EB9-AE1D-89A62449431C}-SdFinish-0
[{05A7B662-80A3-4EB9-AE1D-89A62449431C}-SdWelcome-0]
Result=1
[{05A7B662-80A3-4EB9-AE1D-89A62449431C}-SdLicense2Rtf-0]
Result=1
[{05A7B662-80A3-4EB9-AE1D-89A62449431C}-SdComponentDialog-0]
szDir=C:\oraclexe\
Component-type=string
Component-count=1
Component-0=DefaultFeature
Result=1
[{05A7B662-80A3-4EB9-AE1D-89A62449431C}-AskPort-13013]
TNSPort=1521
MTSPort=2031
HTTPPort=8080
Result=1
[{05A7B662-80A3-4EB9-AE1D-89A62449431C}-AskSYSPassword-13011]
SYSPassword=oraclexe
Result=1
[{05A7B662-80A3-4EB9-AE1D-89A62449431C}-SdStartCopy-0]
Result=1
[{05A7B662-80A3-4EB9-AE1D-89A62449431C}-SdFinish-0]
Result=1
bOpt1=0
bOpt2=0
