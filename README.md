
bspatch

增量更新

使用方法

1.集成module:bspatch

2.使用根目录/tool/bsdiff.exe 生成patch文件，或者用其中的压塑包自己另外编译(demo中自由windows环境，linux环境需要自己编译)
  生成patch的方法  命令行  执行命令  bsdiff oldfile  newfile  patchPath
  其中 oldfile表示老版本apk  newfile表示新版本apk patchPath表示 生成的差分包
  
3.将差分包拷贝到任意目录（实际项目中应该是通过网络下载）

4.调用api合并差分包
   BSPatch.bspatch(new,patch,callback);
   ps:这个地方我做demo为了方便把callback这里直接用了static，实际项目中请重新定义回调
   另外为了保证生成的新apk的有效性 最好进行md5检查  我这里为了简单没有做校验


