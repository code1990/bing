package com;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: bing
 * @Date: 2019-12-07 22:04
 * @Author: code1990
 * @Description:
 */
public class WindowsUtil {
    // 得到进程ID
    public static String getProcessId(int port) {
        InputStream is = null;
        BufferedReader br = null;
        String pid = null;
        try {
            String[] args = new String[]{"cmd.exe", "/c", "netstat -aon|findstr", port + ""};
            is = Runtime.getRuntime().exec(args).getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String temp = br.readLine();
            if (temp != null) {
                String[] strs = temp.split("\\s");
                pid = strs[strs.length - 1];
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pid;
    }

    //根据进程ID得到映像名称
    public static String getProgramName(String pid) {
        InputStream is = null;
        BufferedReader br = null;
        String programName = null;
        try {
            String[] args = new String[]{"cmd.exe", "/c", "tasklist|findstr", pid};
            is = Runtime.getRuntime().exec(args).getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String temp = br.readLine();
            if (temp != null) {
                String[] strs = temp.split("\\s");
                programName = strs[0];
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return programName;
    }

    //根据映像名称关闭进程
    public static void stopProcessName(String programName) {
        String[] args = new String[]{"Taskkill", "/f", "/IM", programName};
        try {
            Runtime.getRuntime().exec(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopProcessId(String pid) {
        // 运行命令
        String[] args = new String[]{"taskkill", "/PID", pid, "/IM"};
        try {
            String str = "cmd.exe /c taskkill /PID "+pid+" /F /T ";
            Runtime.getRuntime().exec(args);
            Runtime.getRuntime().exec(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*   模拟实现jps*/
    public static List<String> getJpsInfo(){
        List<String> list = new ArrayList<>();
        List<VirtualMachineDescriptor> machineDescriptors = VirtualMachine.list();
        for (VirtualMachineDescriptor machineDescriptor : machineDescriptors) {
            list.add(machineDescriptor.id() + "\t" + machineDescriptor.displayName());
        }
        return list;

    }

    public static void main(String[] args) {
        List<String> list = WindowsUtil.getJpsInfo();
        for (int i = 0; i < list.size(); i++) {
            String[] array = list.get(i).split("\t");
            if(array.length==2){
                String pid = array[0];
                String name = array[1].trim();
                if(!"".equals(name)&&name.contains("bing-0.0.1-SNAPSHOT.jar")){
                    WindowsUtil.stopProcessId(pid);
                    break;
                }
            }

        }
    }

}
