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
    
    
    @IBOutlet weak var textField: UITextField!
    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var button: UIButton!
    @IBOutlet weak var textView: UITextView!
    @IBOutlet weak var text: UITextField!
    //    var imagePicker: UIImagePickerController!
    
    var imagesRef:Firebase!
    
    
    private lazy var client : ClarifaiClient = ClarifaiClient(appID: clarifaiClientID, appSecret: clarifaiClientSecret)
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Create a reference to a Firebase location
        //myRootRef = Firebase(url:"https://incandescent-fire-2307.firebaseio.com")
        imagesRef = Firebase(url:"https://radiant-inferno-1334.firebaseio.com")
        // Write data to Firebase
        
        
        // Read data and react to changes
        imagesRef.observeEventType(.Value, withBlock: {
            snapshot in
            print("\(snapshot.key) -> \(snapshot.value)")
        })
        
    }
    
    @IBAction func buttonClicked(sender: AnyObject) {
        
        // Show a UIImagePickerController to let the user pick an image from their library.
        let picker = UIImagePickerController()
        picker.sourceType = .PhotoLibrary
        picker.allowsEditing = false
        picker.delegate = self
        presentViewController(picker, animated: true, completion: nil)
        
        
    }
    
    
    func imagePickerControllerDidCancel(picker: UIImagePickerController) {
        dismissViewControllerAnimated(true, completion: nil)
    }
    
    func imagePickerController(picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : AnyObject]) {
        if let pickedImage = info[UIImagePickerControllerOriginalImage] as? UIImage {
            imageView.contentMode = .ScaleAspectFit
            textView.text = "Recognizing..."
            button.enabled = false
            imageView.image = pickedImage
            recognizeImage(pickedImage)
            
            //print("in imagepickercontroller ", results)

            //let unwrappedImage = pickedImage
            //let data = UIImageJPEGRepresentation(pickedImage, 0.1) // 0.0 to 1.0 - sets jpeg quality

            //print(data)
        }
        dismissViewControllerAnimated(true, completion: nil)

    }
    
    func sendToFirebase(title:String, results:[String]!){
        print(results)
        imagesRef.childByAutoId().setValue([
            "title":title,
            "image":"img_str",
            "results":results
            ])
    }
    
    private func recognizeImage(image: UIImage!){
        // Scale down the image. This step is optional. However, sending large images over the
        // network is slow and does not significantly improve recognition performance.
        let size = CGSizeMake(320, 320 * image.size.height / image.size.width)
        UIGraphicsBeginImageContext(size)
        image.drawInRect(CGRectMake(0, 0, size.width, size.height))
        let scaledImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        
        // Encode as a JPEG.
        let jpeg = UIImageJPEGRepresentation(scaledImage, 0.9)!
        //var txtResults = [String]()
        //var tempResults = [AnyObject]()
        // Send the JPEG to Clarifai for standard image tagging.
        client.recognizeJpegs([jpeg]) {
            (results: [ClarifaiResult]?, error: NSError?) in
            if error != nil {
                print("Error: \(error)\n")
                self.textView.text = "Sorry, there was an error recognizing your image."
            } else {
                print("in recognizeimage 1 ", results![0].tags)
                self.textView.text = "Tags:\n" + results![0].tags.joinWithSeparator(", ")
                //tempResults = self.copyArray(results![0].tags)
                if let title = self.textField.text{
                    self.sendToFirebase(title, results: results![0].tags as [String])

                }
                //let temp = results![0].tags
                //txtResults = temp.map{$0.copy() as! String}
                //txtResults = NSArray(array: temp, copyItems: true) as! [String]
                //print("in recognizeimage 2 ", txtResults)
                //print(txtResults[0] as! String==results![0].tags[0])

                //txtResults.unshare()
            }
            self.button.enabled = true
        }
        //print("in recognizeimage 3 ", txtResults)
    }

    func copyArray(arr:[AnyObject]) -> [AnyObject]{
        var results = [AnyObject]()
        results = arr.map{$0.copy()}
        
        return results
    }
    
    
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
}

