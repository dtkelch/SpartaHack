import Foundation

class Message : NSObject {
    var title_: String
    var imageUrl_: String?
    var results_: [String]
    
    convenience init(title: String?, results: [String]) {
        self.init(title: title, imageUrl: nil, results: results)
    }
    
    init(title: String?, imageUrl: String?, results: [String]) {
        self.title_ = title!
        self.imageUrl_ = imageUrl
        self.results_ = results
    }
    
    func title() -> String! {
        return title_;
    }
    
    func imageUrl() -> String? {
        return imageUrl_;
    }
    
    func results() -> [String] {
        return results_;
    }
}