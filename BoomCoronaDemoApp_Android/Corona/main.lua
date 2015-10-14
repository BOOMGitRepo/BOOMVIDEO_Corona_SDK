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
 
local guid = "91db1880-af5b-4ac9-aab4-a097b5becf3d";


function onTrackEvent(result,points,errorInfo)
  
if result=="AD_VIEW_LOADED" then
            print("AD_VIEW_LOADED..."); 
  end
if result=="AD_VIEW_CLOSED" then
            print("AD_VIEW_CLOSED..."); 
  end
if result=="AD_FAILED" then
            print("AD_FAILED due to..",errorInfo); 
  end
if result=="POINTS_REVEALED" then
            print("POINTS_REVEALED ..",points); 
  end

if result=="SUCCESSFUL_SHARED_ON_FACEBOOK" then
            print("SUCCESSFUL_SHARED_ON_FACEBOOK..."  ); 
  end
if result=="TWITTER_SHARE_COMPLETED" then
            print("TWITTER_SHARE_COMPLETED..."  ); 
  end
if result=="SUCCESSFUL_SHARED_ON_GOOGLEPLUS" then
            print("SUCCESSFUL_SHARED_ON_GOOGLEPLUS..."  ); 
  end
if result=="SUCCESSFUL_SHARED_ON_FACEBOOK" then
            print("SUCCESSFUL_SHARED_ON_FACEBOOK..."  ); 
  end
end	 

-- These are the functions triggered by the buttons
local buttonPrerollPress = function( event )
end

local buttonPrerollRelease = function( event )
library.preroll(guid)
end

local buttonOfferPress = function( event )
end

local buttonOfferRelease = function( event )
library.offer(guid)
end

local buttonRewardPress = function( event )
end

local buttonRewardRelease = function( event )
library.rewards(guid)
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

