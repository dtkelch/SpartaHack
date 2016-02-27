//
//  ViewController.swift
//  firesay
//
//  Created by Daniel Kelch on 2/27/16.
//
//

import UIKit
import Firebase

class ViewController: UIViewController {

    
    @IBOutlet weak var text: UITextField!
    
    var myRootRef:Firebase!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Create a reference to a Firebase location
        myRootRef = Firebase(url:"https://incandescent-fire-2307.firebaseio.com")
        // Write data to Firebase
        
        
        // Read data and react to changes
        myRootRef.observeEventType(.Value, withBlock: {
            snapshot in
            print("\(snapshot.key) -> \(snapshot.value)")
        })
        
    }
    
    @IBAction func buttonClicked(sender: AnyObject) {
        
        myRootRef.setValue(text.text)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

