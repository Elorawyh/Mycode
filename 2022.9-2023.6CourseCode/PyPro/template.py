from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support.expected_conditions import *
import time
import os

def open_driver(path,url):
    options = webdriver.ChromeOptions()
    options.add_experimental_option('excludeSwitches', ['enable-logging']) #for ignore warning and error
    driver = webdriver.Chrome(path,options=options)
    driver.get(url)
    driver.implicitly_wait(7) #set a waiting time limit for the browser driver
    #driver.maximize_window()
    return driver

def search_a_keyword(keyword):
    '''
    find_element(By.name,'q') act equivalant to find_element_by_name('q')
    the method find_element_by_name is deprecated in latest version
    
    To search a keyword you need to fill in the keyword and press enter.
    We use send_keys() function to simulate keyborad inputs.
    ref:https://selenium-python.readthedocs.io/locating-elements.html#
    '''
    search_bar = driver.find_element(By.NAME,"q") #the name of search bar is declare as "q"
    search_bar.send_keys(keyword)  #fill the keyword to the bar
    time.sleep(operate_delay)
    search_bar.send_keys(Keys.RETURN) # press enter to go
    time.sleep(operate_delay)


def get_all_product_description_in_one_page():
    """
    Since rendering a page takes time, you must wait until the rendering is finished.
    If you are trying to get element immediately, you will receive an error telling you the element does not exist.
    There're two ways to wait:
        1) implicit(recommended): use the 'wait.until' provided by selenium, it will wait until all the required element are rendered.
        2) explicit: use time.sleep(5).
            card_bodys = driver.find_element(By.CLASS_NAME,"card-body")
            sleep(5)
    ref:https://selenium-python.readthedocs.io/waits.html#explicit-waits
    ref:https://selenium-python.readthedocs.io/waits.html#implicit-waits
    """
    
    #First we try to get all the products informations in one page, the information is stored in the card-body class
    card_bodys = wait.until(presence_of_all_elements_located((By.CLASS_NAME, "card-body")))
    for card_body in card_bodys:
        #The product link is under tag 'a'
        product_link = card_body.find_element(By.TAG_NAME,'a')
        '''
        There're two ways to click the link
            1) directly click it, the browser will render the page in current tag
            product_link.click()
        
            2) open the link in new window tab(recommended)
            product_link.send_keys(Keys.CONTROL,Keys.RETURN)
        '''

        
        if os.name == 'posix':
            #check Operation System, os.name =='posix' indicate the OS is Mac OS or linux
            product_link.send_keys(Keys.COMMAND,Keys.RETURN)
        else:
            product_link.send_keys(Keys.CONTROL,Keys.RETURN)
        
        windows = driver.window_handles #driver.window_handles is the list of windows tag
        #windows[0]=the homepage
        #windows[-1]=the latest open page 
        
        #switch to handle the new window tab
        driver.switch_to.window(windows[-1])
        list_items = wait.until(presence_of_all_elements_located((By.CLASS_NAME, "list-group-item")))
        time.sleep(operate_delay)
        
        product = {}#using name as key,description as value
        name = list_items[0].text
        description = list_items[3].text
        description = description[description.find(' ')+1:]
        product['name']=name
        product['description']=description
        products.append(product)
        print(product,end='\n\n')
        driver.close()
        time.sleep(operate_delay)
        driver.switch_to.window(windows[0])

def save_the_result(produts):
    import json
    with open('first_page_desctiption.json','w') as f:
        json.dump(produts,f,indent=4)    



#Begin of parameters declaration
keyword = input('Please input a keyword to search:')

"""
To install a chromedriver for launching this script you need to do the followings:
1. Open a chrome page and type chrome://settings/help to check your chrome version
2. find a suitable version from https://chromedriver.chromium.org/downloads
3. Create a folder with name 'chromedriver' in project directory 
4. extract the chromedriver.exe to the folder 'chromedriver'
"""
driver_path = 'C:\Users\Yollie\OneDrive - University of Macau\Jupyter-wkspce\PyPro' #The path where you put the chromedriver.exe in
url = 'http://10.113.178.219' #The url of shopping cart
operate_delay = 1 #The time gap between each process
#End of parameters declaration

driver = open_driver(driver_path,url) 
wait = WebDriverWait(driver, 10) #set a implicit waiting time for the browser driver

# An example of getting all the product description in one page
search_a_keyword(keyword)
products = []  # To store the fetched products information
get_all_product_description_in_one_page()
save_the_result(products)
driver.close()


#---------------------------------------------------------------------------------------
# Task 1: place the order
# implement the following function and run
def place_the_order(product_name:str):
    def login():
        pass
    def search_item(product_name:str):
        pass
    def add_to_cart():
        pass
    def place_order():
        pass
    login()
    search_item(product_name)
    add_to_cart()
    place_order()

place_the_order('IPHONE 11 PRO 256GB MEMORY')

# Task 2: Find the top-5 most expensive products
def Find_top_5():
    top_5_result = []
    product = {'name':'','price':0.0}
    # Please note that the price is a floating number
    def get_all_product_price_in_one_page():
        pass
    def go_to_next_page():
        pass
    def save_dict_to_json():
        import json
        with open('top_5_result.json','w') as f:
            json.dump(top_5_result,f,indent=4)
        pass
    while True:
        get_all_product_price_in_one_page()
        go_to_next_page()
        break
        #you should break the loop if next_page does not exist (the current is the last page)
    save_dict_to_json()
    return top_5_result

Find_top_5()

# Task 3: Place the order to the top-5 products you found in task-2
def order_top5():
    top_5_result = Find_top_5()
    for product in top_5_result:
        place_the_order(product['name'])

order_top5()

# Task 4: Place the order to the limited items
def buy_PS5():
    pass

buy_PS5()