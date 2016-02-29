//
//  ImageTableViewController.swift
//  firesay
//
//  Created by Daniel Kelch on 2/27/16.
//
//

import UIKit
import Firebase

class ImageTableViewController: UITableViewController {

    var results = ["hello1", "hello2", "hello3"]
    var messages = [Message]()
    var imagesRef:Firebase!

    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupFirebase()

        tableView.reloadData()
        print("-----------------------")


        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem()
    }

    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        print(messages)

        tableView.reloadData()
//        print("--------------")
//        print (self.results)
    }
    
    func getResults(){
        
    }
    
    func setupFirebase() {
        // *** STEP 2: SETUP FIREBASE
        imagesRef = Firebase(url:"https://radiant-inferno-1334.firebaseio.com")
        
        // *** STEP 4: RECEIVE MESSAGES FROM FIREBASE (limited to latest 25 messages)
        imagesRef.queryLimitedToNumberOfChildren(25).observeEventType(FEventType.ChildAdded, withBlock: { (snapshot) in
            let title = snapshot.value["title"] as? String
            //let sender = snapshot.value["sender"] as? String
            let imageUrl = snapshot.value["image"] as? String
            let results = snapshot.value["results"] as! [String]
            
            let message = Message(title: title, imageUrl: imageUrl, results: results)
            self.messages.append(message)
            print(self.messages)
        })
    }
    
    override func viewDidAppear(animated: Bool) {
        super.viewDidAppear(animated)
        tableView.reloadData()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // MARK: - Table view data source

    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        print(messages.count)
        return messages.count
    }
    
    override func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        let cell : UITableViewCell = tableView.dequeueReusableCellWithIdentifier("Cell", forIndexPath: indexPath)
        //performSegueWithIdentifier("FriendProfile", sender: nil)
    }
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        var cell: UITableViewCell
        cell = tableView.dequeueReusableCellWithIdentifier("Cell", forIndexPath: indexPath)
        cell.textLabel!.text = self.messages[indexPath.row].title()
        print(self.messages[indexPath.row].title())
        //var viewcontroller = self.storyboard! .instantiateViewControllerWithIdentifier("ProfileDetail")
        
        
        // Configure the cell...
        
        return cell
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        
        if segue.identifier == "imageDetailView" {
            if let indexPath = self.tableView.indexPathForSelectedRow {
                let result = messages[indexPath.row]
                let controller = segue.destinationViewController as! ImageDetailViewController
                controller.result = result
                
            }
            
        }
        
    }



    /*
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("reuseIdentifier", forIndexPath: indexPath)

        // Configure the cell...

        return cell
    }
    */

    /*
    // Override to support conditional editing of the table view.
    override func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if editingStyle == .Delete {
            // Delete the row from the data source
            tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
        } else if editingStyle == .Insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(tableView: UITableView, moveRowAtIndexPath fromIndexPath: NSIndexPath, toIndexPath: NSIndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(tableView: UITableView, canMoveRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
