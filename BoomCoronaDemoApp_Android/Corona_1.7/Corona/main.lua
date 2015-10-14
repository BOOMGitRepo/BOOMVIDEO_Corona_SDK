local library = require "plugin.library"

-- Display the background image.
local background = display.newImage("paper_bkg.png", true)
background.x = display.contentCenterX
background.y = display.contentCenterY
local widget = require( "widget" )

display.setStatusBar( display.HiddenStatusBar )
display.setDefault( "background", 1 )


-- This event is dispatched to the following Lua function
-- by PluginLibrary::show() in PluginLibrary.mm
local function listener( event )
	native.showAlert( event.name, event.message, { "OK" } )
end

--********************************************
 

function onTrackEvent(result,points)
  print("Get called with this result :",result)
  print("Get called with this points :",points)
 if result=="VIDEO_UNAVAILABLE" then
            print("Video unavailable!"); 
  end
 if result=="APP_INSTALLED" then
            print("APP_INSTALLED...",points); 
  end
if result=="AD_VIEW_LOADED" then
            print("AD_VIEW_LOADED...",points); 
  end
if result=="AD_VIEW_CLOSED" then
            print("AD_VIEW_CLOSED...",points); 
  end
  if result=="INTERSTITIAL_CLOSED" then
            print("INTERSTITIAL_CLOSED..."); 
  end
 if result=="INTERSTITIAL_CLICKED" then
            print("INTERSTITIAL_CLICKED..."); 
  end
if result=="INTERSTITIAL_LOADED" then
            print("INTERSTITIAL_LOADED..."); 
  end
if result=="OFFER_AVAILED_ALREADY" then
            print("OFFER_AVAILED_ALREADY..."); 
  end
if result=="VIDEO_COMPLETED_25P" then
            print("VIDEO_COMPLETED_25 precent..."); 
  end
if result=="VIDEO_COMPLETED_50P" then
            print("VIDEO_COMPLETED_50 precent..."); 
  end
if result=="VIDEO_COMPLETED_75P" then
            print("VIDEO_COMPLETED_75 precent..."); 
  end
if result=="BLOG_URL" then
            print("BLOG_URL  ..."); 
  end
if result=="SURVEY_COMPLETED" then
            print("SURVEY_COMPLETED release points ...",points); 
  end
if result=="SLIDESHARE_URL" then
            print("SLIDESHARE_URL release points ...",points); 
  end
if result=="INSTAGRAM_URL" then
            print("INSTAGRAM_URL release points ...",points); 
  end
if result=="VIDEO_PAUSED" then
            print("VIDEO_PAUSED..."  ); 
  end
if result=="TWITTER_SHARE_COMPLETED" then
            print("TWITTER_SHARE_COMPLETED..."  ); 
  end
if result=="GOOGLE_SHARE_COMPLETED" then
            print("GOOGLE_SHARE_COMPLETED..."  ); 
  end
if result=="FACEBOOK_SHARE_COMPLETED" then
            print("FACEBOOK_SHARE_COMPLETED..."  ); 
  end
if result=="VIDEO_CALLBACK_FIRETIME_COMPLETED" then
            print("VIDEO_CALLBACK_FIRETIME_COMPLETED  release points ...",points); 
  end
if result=="ANNOTATION_CLICK" then
            print("ANNOTATION_CLICK ...."); 
  end
if result=="VIDEO_END_PLAY" then
            print("VIDEO_END_PLAY ...."); 
  end
if result=="VIDEO_FIRST_PLAY" then
            print("VIDEO_FIRST_PLAY ...."); 
  end
end	 

-- These are the functions triggered by the buttons
local buttonPrerollPress = function( event )
end

local buttonPrerollRelease = function( event )
library.preroll("9c6543a6-a41e-4657-9178-84210b22d794")
end

local buttonOfferPress = function( event )
end

local buttonOfferRelease = function( event )
library.offer("9c6543a6-a41e-4657-9178-84210b22d794")
end

local buttonRewardPress = function( event )
end

local buttonRewardRelease = function( event )
library.rewards("9c6543a6-a41e-4657-9178-84210b22d794")
end

-- This button has individual press and release functions
-- (The label font defaults to native.systemFontBold if no font is specified)



local buttonPreroll = widget.newButton
{
defaultFile = "buttonRed.png",
overFile = "buttonRedOver.png",
label = "Preroll",
emboss = true,
onPress = buttonPrerollPress,
onRelease = buttonPrerollRelease,
}

local buttonOffer = widget.newButton
{
defaultFile = "buttonRed.png",
overFile = "buttonRedOver.png",
label = "Offer",
emboss = true,
onPress = buttonOfferPress,
onRelease = buttonOfferRelease,
}

local buttonReward = widget.newButton
{
defaultFile = "buttonRed.png",
overFile = "buttonRedOver.png",
label = "Reward",
emboss = true,
onPress = buttonRewardPress,
onRelease = buttonRewardRelease,
}

library.register("onTrackEvent")

buttonPreroll.x = 160; buttonPreroll.y = 160
buttonOffer.x = 160; buttonOffer.y = 240
buttonReward.x = 160; buttonReward.y = 320
--*******************************************

