; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

#define MyAppName "Midi Message Changer"
#define MyAppVersion "0.0.2"
#define MyAppPublisher "Damian Matwiszyn"
#define MyAppURL "https://github.com/Sk0tCraft/MidiMessageChanger"
#define MyAppExeName "MidiMessageChanger.exe"

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{4205A336-428C-4298-B1CE-DBE9FCB0ECDC}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
;AppVerName={#MyAppName} {#MyAppVersion}
AppPublisher={#MyAppPublisher}
AppPublisherURL={#MyAppURL}
AppSupportURL={#MyAppURL}
AppUpdatesURL={#MyAppURL}
DefaultDirName={pf}\{#MyAppName}
DefaultGroupName={#MyAppName}
AllowNoIcons=yes
LicenseFile=C:\Users\Grzegorz\Desktop\Java\MidiMessageChanger\installer\LICENCE.txt
InfoAfterFile=C:\Users\Grzegorz\Desktop\Java\MidiMessageChanger\installer\INFO_AFTER.txt
OutputDir=C:\Users\Grzegorz\Desktop\Java\MidiMessageChanger\installer
OutputBaseFilename=mmc-0.0.2-setup
Compression=lzma
SolidCompression=yes

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Files]
Source: "C:\Users\Grzegorz\Desktop\Java\MidiMessageChanger\out\artifacts\MidiMessageChanger\bundles\MidiMessageChanger.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Users\Grzegorz\Desktop\Java\MidiMessageChanger\out\artifacts\MidiMessageChanger\bundles\MidiMessageChanger\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
Name: "{group}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"
Name: "{group}\{cm:UninstallProgram,{#MyAppName}}"; Filename: "{uninstallexe}"
Name: "{commondesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; Tasks: desktopicon

[Run]
Filename: "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: nowait postinstall skipifsilent
