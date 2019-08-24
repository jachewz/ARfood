//
//  ARViewController.swift
//  StandardCyborgExample
//
//  Created by Aung Paing myo on 8/24/19.
//  Copyright © 2019 Standard Cyborg. All rights reserved.
//

import Foundation
import ARKit
import StandardCyborgFusion
import StandardCyborgUI
import UIKit
import SceneKit.ModelIO

class ARViewController: UIViewController{
    @IBOutlet weak var sceneView: ARSCNView!
    let confiuration = ARWorldTrackingConfiguration()
    
    
   // var foodNode: SCNNode!
    
    //
    private var lastScanPointCloud: SCPointCloud?
    private var lastScanDate: Date?
    private var lastScanThumbnail: UIImage?
    
    private lazy var documentsURL = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first!
    private lazy var scanPLYURL = documentsURL.appendingPathComponent("Cat.ply")
    private lazy var scanThumbnailURL = documentsURL.appendingPathComponent("Cat.jpeg")
    
    override func viewDidLoad(){
        super.viewDidLoad()
       
        self.sceneView.debugOptions = [ARSCNDebugOptions.showFeaturePoints,ARSCNDebugOptions.showWorldOrigin]
        self.sceneView.session.run(confiuration)
        
        //self.initializefoodNode()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
   
    
    
    /*
    func initializefoodNode(){
        let foodScene =  (named: " ")
        self.foodNode = foodScene?.rootNode.childNode(withName: "foodNode", recursively: true)
    }
    */
    
    //
    @IBAction func add(_ sender: Any) {
        loadScan();
        
       // node.position = SCNVecotor3(0,0,0)
        
        
        
    }
    
    private func loadScan() {
        let scanPLYPath = scanPLYURL.path
        let scanThumbnailPath = scanThumbnailURL.path
        let fileManager = FileManager.default
        
        if
            fileManager.fileExists(atPath: scanPLYPath),
            let plyAttributes = try? fileManager.attributesOfItem(atPath: scanPLYPath),
            let dateCreated = plyAttributes[FileAttributeKey.creationDate] as? Date,
            let pointCloud = SCPointCloud(plyPath: scanPLYPath),
            pointCloud.pointCount > 0
        {
            lastScanPointCloud = pointCloud
            lastScanDate = dateCreated
            lastScanThumbnail = UIImage(contentsOfFile: scanThumbnailPath)
        }
        
        DispatchQueue.global(qos: DispatchQoS.QoSClass.userInitiated).async {
            let operation = SCMeshingOperation(inputPLYPath: scanPLYPath, outputPLYPath: scanPLYPath)
            operation.resolution = 10
            operation.smoothness = 0
           // operation.progressHandler = { progress in print("MESH PROGRESS: \(progress)") }
            
        
        
            operation.start()
        
            let mdlAsset = MDLAsset(url: URL(fileURLWithPath: scanPLYPath))
            let mdlObject = mdlAsset.object(at: 0)
            let foodNode = SCNNode(mdlObject: mdlObject)
            foodNode.position = SCNVector3(0,0,0)
            foodNode.eulerAngles = SCNVector3(x:0, y:0, z:1.67)
            self.sceneView.scene.rootNode.addChildNode(foodNode)
       
        }
    }
    
//    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
//        guard let touch = touches.first else {return}
//        let result = sceneView.hitTest(touch.location(in: sceneView), types: [ARHitTestResult.ResultType.featurePoint])
//        guard let hitResult = result.last else {return}
//        let hitTransform = SCNMatrix4.init(hitResult.worldTransform)
//        let hitVector = SCNVector3Make(hitTransform.m41, hitTransform.m42, hitTransform.m43)
//        loadScanPlace(position:hitVector)
//    }
//
//    private func loadScanPlace(position: SCNVector3) {
//        let scanPLYPath = scanPLYURL.path
//        let scanThumbnailPath = scanThumbnailURL.path
//        let fileManager = FileManager.default
//
//        if
//            fileManager.fileExists(atPath: scanPLYPath),
//            let plyAttributes = try? fileManager.attributesOfItem(atPath: scanPLYPath),
//            let dateCreated = plyAttributes[FileAttributeKey.creationDate] as? Date,
//            let pointCloud = SCPointCloud(plyPath: scanPLYPath),
//            pointCloud.pointCount > 0
//        {
//            lastScanPointCloud = pointCloud
//            lastScanDate = dateCreated
//            lastScanThumbnail = UIImage(contentsOfFile: scanThumbnailPath)
//        }
//
//        DispatchQueue.global(qos: DispatchQoS.QoSClass.userInitiated).async {
//            let operation = SCMeshingOperation(inputPLYPath: scanPLYPath, outputPLYPath: scanPLYPath)
//            operation.resolution = 10
//            operation.smoothness = 10
//            operation.progressHandler = { progress in print(progress)}
//
//            operation.start()
//
//            let mdlAsset = MDLAsset(url: URL(fileURLWithPath: scanPLYPath))
//            let mdlObject = mdlAsset.object(at: 0)
//            let foodNode = SCNNode(mdlObject: mdlObject)
//            foodNode.position = position
//            foodNode.eulerAngles = SCNVector3(x:0, y:Float.pi, z:Float.pi)
//            self.sceneView.scene.rootNode.addChildNode(foodNode)
//
//        }
//    }
}
