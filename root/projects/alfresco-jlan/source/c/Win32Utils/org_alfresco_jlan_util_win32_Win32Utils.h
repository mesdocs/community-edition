/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_alfresco_jlan_util_win32_Win32Utils */

#ifndef _Included_org_alfresco_jlan_util_win32_Win32Utils
#define _Included_org_alfresco_jlan_util_win32_Win32Utils
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     org_alfresco_jlan_util_win32_Win32Utils
 * Method:    SetWorkingSetSize
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_org_alfresco_jlan_util_win32_Win32Utils_SetWorkingSetSize
  (JNIEnv *, jclass, jlong, jlong);

/*
 * Class:     org_alfresco_jlan_util_win32_Win32Utils
 * Method:    MapNetworkDrive
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZ)I
 */
JNIEXPORT jint JNICALL Java_org_alfresco_jlan_util_win32_Win32Utils_MapNetworkDrive
  (JNIEnv *, jclass, jstring, jstring, jstring, jstring, jboolean, jboolean);

/*
 * Class:     org_alfresco_jlan_util_win32_Win32Utils
 * Method:    DeleteNetworkDrive
 * Signature: (Ljava/lang/String;ZZ)I
 */
JNIEXPORT jint JNICALL Java_org_alfresco_jlan_util_win32_Win32Utils_DeleteNetworkDrive
  (JNIEnv *, jclass, jstring, jboolean, jboolean);

#ifdef __cplusplus
}
#endif
#endif
