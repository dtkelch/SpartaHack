//
//  ImageDetailViewController.swift
//  firesay
//
//  Created by Daniel Kelch on 2/27/16.
//
//

import UIKit

class ImageDetailViewController: UIViewController {

    
    @IBOutlet weak var textView: UITextView!
    
    var result: Message?
    
    lazy var config: NSURLSessionConfiguration = NSURLSessionConfiguration.defaultSessionConfiguration()
    lazy var session: NSURLSession = NSURLSession(configuration: self.config)
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.textView.text = result?.results().joinWithSeparator(", ")

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func getSyllables(word: String){
        
        
           
        // Setup the session to make REST GET call.  Notice the URL is https NOT http!!
        let session = NSURLSession.sharedSession()
        let url: NSURL = NSURL(string: "https://wordsapiv1.p.mashape.com/words/\(word)/syllables?api_key=ZvAib5xwOqmshoTf4KAqQUkvn2RTp1Pkv8Sjsnbabeq6tigGCb")!
        //var headers = ["X-Mashape-Key": "ZvAib5xwOqmshoTf4KAqQUkvn2RTp1Pkv8Sjsnbabeq6tigGCb", "Accept":"application/json"]
        // Make the POST call and handle it in a completion handler
        session.dataTaskWithURL(url, completionHandler: { ( data: NSData?, response: NSURLResponse?, error: NSError?) -> Void in
            // Make sure we get an OK response
            guard let realResponse = response as? NSHTTPURLResponse where
                realResponse.statusCode == 200 else {
                    print("Not a 200 response")
                    return
            }
            
            // Read the JSON
            do {
                if let ipString = NSString(data:data!, encoding: NSUTF8StringEncoding) {
                    // Print what we got from the call
                    print(ipString)
                    
                    // Parse the JSON to get the IP
                    let jsonDictionary = try NSJSONSerialization.JSONObjectWithData(data!, options: NSJSONReadingOptions.MutableContainers) as! NSDictionary
                    let origin = jsonDictionary["origin"] as! String
                    
                    // Update the label
                    self.performSelectorOnMainThread("updateIPLabel:", withObject: origin, waitUntilDone: false)
                }
            } catch {
                print("bad things happened")
            }
        }).resume()

    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
