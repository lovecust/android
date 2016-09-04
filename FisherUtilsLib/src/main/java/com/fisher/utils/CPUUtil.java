package com.fisher.utils;

import java.io.FileInputStream;
import java.io.InputStream;

public class CPUUtil {

	// 获取CPU最大频率（单位KHZ）
	// "/system/bin/cat" 命令行
	// "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" 存储最大频率的文件的路径
	public static String getMaxCpuFreq( ) throws Exception {
		String result = "";
		ProcessBuilder cmd;
		String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
		cmd = new ProcessBuilder( args );
		Process process = cmd.start();
		InputStream in = process.getInputStream();
		result = StreamUtil.getString( in, "UTF-8" );
		return result.trim();
	}

	// 获取CPU最小频率（单位KHZ）
	public static String getMinCpuFreq( ) throws Exception {
		String result = "";
		ProcessBuilder cmd;
		String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
		cmd = new ProcessBuilder( args );
		Process process = cmd.start();
		InputStream in = process.getInputStream();
		result = StreamUtil.getString( in, "UTF-8" );
		return result.trim();
	}

	// 实时获取CPU当前频率（单位KHZ）
	public static String getCurCpuFreq( ) throws Exception {
		String result = "N/A";
		FileInputStream in = new FileInputStream( "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq" );
		result = StreamUtil.getString( in, "UTF-8" );
		return result;
	}

	// 获取CPU名字
	public static String getCpuName( ) throws Exception {
		String result = "N/A";
		FileInputStream in = new FileInputStream( "/proc/cpuinfo" );
		result = StreamUtil.getString( in, "UTF-8" );
		return result;
	}
}
