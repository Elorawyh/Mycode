# 清洗无用字符
import re
# 读取，# 且忽略非法字符
with open('train.tags.zh-en.en', 'r', encoding='UTF-8') as f:
# with open('train.tags.zh-en.zh', 'r', encoding='UTF-8') as f:
    # # 去掉换行符
    f1 = f.read()# .splitlines()
    # 仅读取字母
    f2 = re.sub('^a-zA-Z', ' ', f1)
    # # 仅读取中文
    # f2 = re.sub('^\u4E00-\u9FA5', ' ', f1)
    # f2 = ''.join(re.findall(r'[\u4E00-\u9FA5\s]', str(f1)))
    for w in f2:
        # 追加写入文件\n",
        with open('train.clean.en', mode='a') as rslt:
        # with open('train.clean.zh', mode='a') as rslt:
            rslt.write(w)