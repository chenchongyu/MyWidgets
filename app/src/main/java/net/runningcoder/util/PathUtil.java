
package net.runningcoder.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import net.runningcoder.R;

import java.io.File;


public class PathUtil {
    private static Context mContext;
    private static PathUtil mUniqueInstance;
    
    private PathUtil() {
    }

    public static void initial(Context context) {
        mContext = context;
    }

    public static PathUtil getInstance() {
        if (mUniqueInstance == null) {
            mUniqueInstance = new PathUtil();
        }
        return mUniqueInstance;
    }

    /*
     * 文件路径
     */
    public File getLocalFile(String remoteFilePath, String account) {
        return new File(getCacheRootPath(account),remoteFilePath);
    }

    public File getRmoteFile(String localFilePath, String account) {
        if (localFilePath.startsWith(getCacheRootPath(account))
                && localFilePath.length() > getCacheRootPath(account).length()) {

            return new File(localFilePath.substring(getCacheRootPath(account)
                    .length()));
        }

        return null;
    }

    /*
     * 缓存根目录
     */
    public String getCacheRootPath(String account) {
        String rootPath = "";
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
			L.i("==================Environment.getExternalStorageDirectory():"+Environment.getExternalStorageDirectory().getAbsolutePath());
            rootPath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath()
                    + "/"
                    + mContext.getString(R.string.app_name) + "/" + account;
        } else {
            rootPath = mContext.getCacheDir().getAbsolutePath() + "/"
                    + mContext.getString(R.string.app_name) + "/" + account;
        }
        File rootFile = new File(rootPath);
        if (!rootFile.isDirectory()) {
            rootFile.mkdirs();
        }
        return rootPath;
    }

    public static String parseName(String filePath) {
        String name = "";
        if (!TextUtils.isEmpty(filePath)) {
            int len = filePath.length();
            int lastFileSeparatorIndex = filePath.lastIndexOf("/");
            if ((len - 1) == lastFileSeparatorIndex) {
                if (len != 1) {
                    String newPath = filePath.substring(0, len - 1);
                    name = parseName(newPath);
                }
            } else {
                name = filePath.substring(lastFileSeparatorIndex + 1);
            }
        }
        return name;
    }
    
    public static String getExtFromFilename(String filename) {
    	 if (TextUtils.isEmpty(filename)) {
    		 return "";
    	 }
        int dotPosition = filename.lastIndexOf('.');
        if (dotPosition != -1) {
            return filename.substring(dotPosition + 1, filename.length());
        }
        return "";
    }
    
    public static String collatePath(String dirtyPath) {
        if (dirtyPath == null) {
            return null;
        }

        return new File("/" + dirtyPath).getPath();
    }
    
    public static int getDepth(String path) {
        if (path == null) {
            return 0;
        }
        return Math.max(0, path.split("/").length - 1);
    }
    
    /**
	 * * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * *
	 * 
	 * @param context
	 */
	public static void cleanInternalCache(Context context) {
		deleteFilesByDirectory(context.getCacheDir());
	}

	/**
	 * * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * *
	 * 
	 * @param context
	 */
	public static void cleanDatabases(Context context) {
		deleteFilesByDirectory(new File("/data/data/"
				+ context.getPackageName() + "/databases"));
	}

	/**
	 * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) *
	 * 
	 * @param context
	 */
	public static void cleanSharedPreference(Context context) {
		deleteFilesByDirectory(new File("/data/data/"
				+ context.getPackageName() + "/shared_prefs"));
	}

	/**
	 * * 按名字清除本应用数据库 * *
	 * 
	 * @param context
	 * @param dbName
	 */
	public static void cleanDatabaseByName(Context context, String dbName) {
		context.deleteDatabase(dbName);
	}

	/**
	 * * 清除/data/data/com.xxx.xxx/files下的内容 * *
	 * 
	 * @param context
	 */
	public static void cleanFiles(Context context) {
		deleteFilesByDirectory(context.getFilesDir());
	}

	/**
	 * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
	 * 
	 * @param context
	 */
	public static void cleanExternalCache(Context context) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			deleteFilesByDirectory(context.getExternalCacheDir());
		}
	}
	/**
	 * * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * *
	 * 
	 * @param filePath
	 * */
	public static void cleanCustomCache(String filePath) {
		deleteFilesByDirectory(new File(filePath));
	}

	/**
	 * * 清除本应用所有的数据 * *
	 * 
	 * @param context
	 * @param filepath
	 */
	public static void cleanApplicationData(Context context, String... filepath) {
		cleanInternalCache(context);
		cleanExternalCache(context);
		cleanDatabases(context);
		cleanSharedPreference(context);
		cleanFiles(context);
		if (filepath == null) {
			return;
		}
		for (String filePath : filepath) {
			cleanCustomCache(filePath);
		}
	}

	/**
	 * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * *
	 * 
	 * @param directory
	 */
	private static void deleteFilesByDirectory(File directory) {
		if (directory != null && directory.exists() && directory.isDirectory()) {
			for (File item : directory.listFiles()) {
				item.delete();
			}
		}
	}
	
	// 获取文件
	//Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
	//Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
	public static long getFolderSize(File file) throws Exception {
		long size = 0;
		try {
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				// 如果下面还有文件
				if (fileList[i].isDirectory()) {
					size = size + getFolderSize(fileList[i]);
				} else {
					size = size + fileList[i].length();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}
	
	/**
	 * 删除指定目录下文件及目录
	 * 
	 * @param deleteThisPath
	 * @param deleteThisPath
	 * @return
	 */
	public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
		if (!TextUtils.isEmpty(filePath)) {
			try {
				File file = new File(filePath);
				if (file.isDirectory()) {// 如果下面还有文件
					File files[] = file.listFiles();
					for (int i = 0; i < files.length; i++) {
						deleteFolderFile(files[i].getAbsolutePath(), true);
					}
				}
				if (deleteThisPath) {
					if (!file.isDirectory()) {// 如果是文件，删除
						file.delete();
					} else {// 目录
						if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
							file.delete();
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

}
