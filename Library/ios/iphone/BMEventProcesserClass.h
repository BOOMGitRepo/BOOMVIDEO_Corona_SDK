//
//  BMEventProcesserClass.h
//  Plugin
//
//  Created by Sachin Patil on 19/06/15.
//
//

#import <Foundation/Foundation.h>
#import "BoomVideoTrackerDelegate.h"
#include "CoronaLua.h"
#include "CoronaMacros.h"
#import "PluginLibrary.h"

@interface BMEventProcesserClass : NSObject <BoomVideoTrackerDelegate>
+(BMEventProcesserClass *) sharedInstance;
@property (nonatomic) void(*luaplugincallback)(BOOMEventCode, NSDictionary*);
//@property (nonatomic, assign) id<BoomVideoTrackerDelegate> videoTrackerInfoDelegate;
@end
