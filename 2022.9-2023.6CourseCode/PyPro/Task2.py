from selenium.webdriver.common.by import By
from selenium.webdriver.support.expected_conditions import *
import time
from main import driver
from main import wait
from main import login

# products
prods_name = []
prods_price = []

# login()
#---------------------------------------------------------------------------------------
# Task 2: Find the top-5 most expensive products
def Find_top_5():
    top_5_result = []
    products = {'name':'','price':0.0}
    # Please note that the price is a floating number
    
    def get_all_product_price_in_one_page():
        # name
        prodsn = wait.until(presence_of_all_elements_located((By.CLASS_NAME, "card-body")))
        for prodn in prodsn:
            #The product link is under tag 'strong'
            n = prodn.find_element(By.TAG_NAME,'strong').text
            prods_name.append(n)
        # print(prods_name)
        
        # price
        prodsp = wait.until(presence_of_all_elements_located((By.CLASS_NAME, "card-body")))
        for prodp in prodsp:
            #The product link is under tag 'h3'
            p = prodp.find_element(By.TAG_NAME,'h3').text[1:]
            # print(p)
            prods_price.append(float(p))
        # print(prods_price)
    
    def go_to_next_page(current):
        driver.execute_script("window.scrollTo(0,document.body.scrollHeight)")
        time.sleep(2)
        # nextpage
        nextpage = driver.find_element(By.LINK_TEXT, '{p}'.format(p=current))
        nextpage.click()
        wait
        # t = random.randint(1,2) # 页面停留时间随机，防止被和谐
        # time.sleep(t)
        # # #将滚动条移动到页面的顶部  
        # driver.execute_script("window.scrollTo(document.body.scrollHeight,0)") 
        # wait
    
    def save_dict_to_json():
        import json
        with open('top_5_result.json','w') as f:
            json.dump(top_5_result,f,indent=4)
    
    # get pages
    pages = wait.until(presence_of_all_elements_located((By.CLASS_NAME, "page-link")))
    for current in range(2,len(pages)+1):
        get_all_product_price_in_one_page()
        go_to_next_page(current)
        #you should break the loop if next_page does not exist (the current is the last page)
    products = dict(zip(prods_name, prods_price))
    print(products)
    # value 降序
    s_prods = sorted(products.items(),key = lambda i: i[1],reverse=True)
    # print(s_prods)
    # 取前5个键值对
    for i in range(0, 5):
        t = s_prods[i]
        d = {"name":t[0], "price":t[1]}
        top_5_result.append(d)
    print(top_5_result)
    save_dict_to_json()
    return top_5_result

# Find_top_5()
