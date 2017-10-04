;----------------------------------------------------------------
;
; $Id$
; $URL$
;
; COPYRIGHT 2017, TIOBE Software B.V.  --  All rights reserved.
;
; Description:
;  IntelliJ Integration Component Installation Code
;
;----------------------------------------------------------------

LangString NAME_SECTION_IDEINTELLIJ  ${LANG_ENGLISH} "IntelliJ add-in" 
LangString TEXT_INTELLIJLOCATION_TITLE ${LANG_ENGLISH} "IntelliJ add-in"
LangString TEXT_INTELLIJLOCATION_SUBTITLE ${LANG_ENGLISH} "Set the folder in which the IntelliJ environment is installed."

Var INTELLIJPATH ; path to IntelliJ installation
Var IDEINTELLIJ

!define INTELLIJ_PLUGIN_NAME "TICSIntelliJ"

Function InstallIntelliJ
  ${If} $INTELLIJPATH != ""
    !insertmacro SETOUTPATH "$TEMP"
    SetOverwrite on
    File "${TICS_COMPONENTS_ROOT}\ideintellij\build\distributions\${INTELLIJ_PLUGIN_NAME}.zip"
    !insertmacro EXTRACTZIP "${INTELLIJ_PLUGIN_NAME}" "$INTELLIJPATH\plugins"
    !insertmacro REGISTRY_WRITE "${UnRegKey}" "INTELLIJPATH" "$INTELLIJPATH" "REG_SZ"
  ${Endif}
FunctionEnd

Function initIntelliJ
  StrCmp $INIFILE "" noinifileintellij
  !insertmacro READINIVALUE $INIFILE "IntelliJ" "InstallPath" $INTELLIJPATH ""  
noinifileintellij:
FunctionEnd

Var INTELLIJPAGEFILE
Var INTELLIJPATHEDITED

Function intellijPage
  ${If} $IDEINTELLIJ == 1
    ${If} $INTELLIJPATH == "" ; if the IntelliJ path has been set skip this page
    ${OrIf} $INTELLIJPATHEDITED == 1 ; unless it has been edited (e.g. when going back)
      !insertmacro MUI_HEADER_TEXT $(TEXT_INTELLIJLOCATION_TITLE) $(TEXT_INTELLIJLOCATION_SUBTITLE)		
      GetTempFileName $INTELLIJPAGEFILE
      File /oname=$INTELLIJPAGEFILE ${TICS_COMPONENTS_ROOT}\ideintellij\intellijlocation.ini
      WriteINIStr $INTELLIJPAGEFILE "Field 3" "State" $INTELLIJPATH ; field 3 = IntelliJ installation path
      InstallOptions::dialog $INTELLIJPAGEFILE
      ReadINIStr $INTELLIJPATH $INTELLIJPAGEFILE "Field 3" "State" ; field 3 = IntelliJ installation path
      StrCpy $INTELLIJPATHEDITED 1
    ${Endif}
  ${Endif}
FunctionEnd

;----------------------------------------------------------------
; Uninstall Section

Function un.intellij
  !insertmacro REGISTRY_READ "${UnRegKey}" "INTELLIJPATH" "$R0"
  RMDir /r "$R0\plugins\${INTELLIJ_PLUGIN_NAME}"
FunctionEnd
