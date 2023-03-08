from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support.expected_conditions import *
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
    # time.sleep(operate_delay)
    wait
    search_bar.send_keys(Keys.RETURN) # press enter to go
    # time.sleep(operate_delay)
    wait


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
        wait
        
        product = {}#using name as key,description as value
        name = list_items[0].text
        description = list_items[3].text
        description = description[description.find(' ')+1:]
        product['name']=name
        product['description']=description
        product.append(product)
        print(product,end='\n\n')
        driver.close()
        wait
        driver.switch_to.window(windows[0])

def save_the_result(produts):
    import json
    with open('first_page_desctiption.json','w') as f:
        json.dump(produts,f,indent=4)    


#Begin of parameters declaration
# keyword = input('Please input a keyword to search:')

"""
To install a chromedriver for launching this script you need to do the followings:
1. Open a chrome page and type chrome://settings/help to check your chrome version
2. find a suitable version from https://chromedriver.chromium.org/downloads
3. Create a folder with name 'chromedriver' in project directory 
4. extract the chromedriver.exe to the folder 'chromedriver'
"""
driver_path = r'C:\Users\Yollie\OneDrive - University of Macau\Jupyter-wkspce\PyPro\chromedriver.exe'
#The path where you put the chromedriver.exe in
url = 'http://10.113.178.219/login' #The url of login page
# operate_delay = 1 #The time gap between each process
#End of parameters declaration

driver = open_driver(driver_path,url) 
# wait = WebDriverWait(driver, 5) #set a implicit waiting time for the browser driver
wait = WebDriverWait(driver, 3) # 抢购版  # 好像正常也能用，那干脆踩油门加速:)

def login():
        # account
        t_username = driver.find_element(By.ID,'email')
        t_username.send_keys('mc25661@um.edu.mo')
        # password
        t_password = driver.find_element(By.ID,'password')
        t_password.send_keys('315315')
        # login
        t_login = driver.find_element(By.XPATH,"//button[@class='btn btn-primary']")
        t_login.click()