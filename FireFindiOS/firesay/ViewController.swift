//
//  ViewController.swift
//  firesay
//
//  Created by Daniel Kelch on 2/27/16.
//
//

import UIKit
import Firebase

class ViewController: UIViewController, UINavigationControllerDelegate, UIImagePickerControllerDelegate {

    
    @IBOutlet var imageView: UIImageView!
    @IBOutlet weak var text: UITextField!
//    var imagePicker: UIImagePickerController!
    var imagePicker = UIImagePickerController()

    
    var myRootRef:Firebase!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        imagePicker.delegate = self

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
        
        //  myRootRef.setValue(text.text)
        imagePicker.sourceType = .PhotoLibrary
        
        presentViewController(imagePicker, animated: true, completion: nil)
    }
    
func imagePickerController(picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : AnyObject]) {
    if let pickedImage = info[UIImagePickerControllerOriginalImage] as? UIImage {
            imageView.contentMode = .ScaleAspectFit
            imageView.image = pickedImage
        let unwrappedImage = pickedImage
        let data = UIImageJPEGRepresentation(unwrappedImage, 0.1) // 0.0 to 1.0 - sets jpeg quality
        print(data)
    }
    
        imagePicker.dismissViewControllerAnimated(true, completion: nil)
    }
    


    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

