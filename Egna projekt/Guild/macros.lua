function()
    if (GetUnitSpeed('player') > 0 and math.random() < 0.02) then
        --hej(1);
    end
    
    function hej(n)
        local x = GetScreenWidth();
        local y = GetScreenHeight();
        for i = 1,n do
            local m = CreateFrame("Button","m",UIParent,"UIPanelButtonTemplate");
            m:SetPoint("CENTER",x/2-math.random(x), y/2-math.random(y)); 
            m:SetWidth(20); 
            m:SetHeight(20);
            m:SetText(string.char(math.random(32, 126)))
            m:RegisterForClicks("AnyDown")
            m:SetScript("OnClick", function(self,a) hej(math.min(1000,10*n)) end)
        end
    end
    
    
    
    return 'RUN TO ME!'
    
end



/run function h(n)w=GetScreenHeight()d=w/2;r=math.random;for i=1,n do m=CreateFrame("Button","m",UIParent,"UIPanelCloseButton")m:SetPoint("LEFT",r(2*w),d-r(w))m:SetScript("OnClick",function(s,a)h(math.min(999,9*n))end)end;end;h(9)

UIPanelCloseButton
MagicButtonTemplate
OptionsButtonTemplate
UIPanelButtonTemplate
GameMenuButtonTemplate

/run function h(n)w=GetScreenHeight()d=w/2;r=math.random;m=CreateFrame("Button","m",UIParent,"UIPanelButtonTemplate");m:SetPoint("CENTER",1.8*d-r(1.8*w),d-r(w));m:SetWidth(22); if n>0 then m:SetScript("OnUpdate",function(s,a)h(0)end);end;end;h(1)


/run function h(n)w=GetScreenHeight()x,y=GetCursorPosition()s=768/w;m=CreateFrame("Button","m",UIParent,"UIPanelCloseButton");m:SetPoint("BOTTOMLEFT",x/s,y/s); if n>0 then m:SetScript("OnUpdate",function(s,a)h(0)end)end;end;h(1)

/run 

function h(n,a)
    print(a:GetChecked())
    w=GetScreenHeight()
    d=w/2;
    r=math.random;
    for i=1,n do 
        m=CreateFrame("Button","m",UIParent,"UIPanelCloseButton")
        m:SetPoint("LEFT",r(2*w),d-r(w))
        m:SetScript("OnClick",
            function(s,a)
                s:SetChecked(true)h(math.min(999,9*n),s)
            end
        )
    end;
end;

h(9)


/run function h()a="CheckButton"w=GetScreenHeight()d=w/2;r=math.random;m=CreateFrame(a,"",UIParent,"UI"..a.."Template")m:SetFrameStrata("TOOLTIP")m:SetPoint("LEFT",r(2*w),d-r(w))m:SetScript("OnUpdate",function(s,a)if s:GetChecked()then h()end;end)end;h()


/run function h(n)w=GetScreenHeight()x,y=GetCursorPosition()s=768/w;m=CreateFrame("Button","m",UIParent,"UIPanelCloseButton");m:SetPoint("BOTTOMLEFT",x/s,y/s); if n>0 then m:SetScript("OnUpdate",function(s,a)if IsAltKeyDown() then h(0)end end)end;end;h(1)


/run function h(n)m=CreateFrame("Button","",UIParent,"UIPanelCloseButton");m:SetPoint("CENTER",a,b)if n>0 then m:SetScript("OnUpdate",function(s,e)h(0);a,b=a+c[(x+1)%4+1],b+c[x%4+1]if IsAltKeyDown()then x=x+1 end;end);end;end;x,a,b,c=0,0,0,{0,1,0,-1}h(1)



/run 

if snake==nil then
    function h(n)
        m=CreateFrame("Button","",UIParent,"UIPanelCloseButton");
        m:SetPoint("CENTER",a,b);
    end;


    x,a,b,c,d,start=0,0,0,{0,25,0,-25},true,false
    snake = snake or CreateFrame("Frame");
    snake.t=0;
    snake:SetScript("OnUpdate",
        function(s,e)
            if snake.t>1 and start then
                h(0);
                a,b=a+c[(x+1)%4+1],b+c[x%4+1]
                if IsLeftAltKeyDown() then 
                    x=d and x+1 or x
                    d=false
                    if IsRightAltKeyDown() then
                        start=true
                    end
                elseif IsRightAltKeyDown() then
                    x=d and x-1 or x
                    d=false
                else
                    d=true;
                end;
                snake.t=0;
            else
                snake.t=snake.t+e;
            end;
        end);
end




/run function h(n)w=GetScreenHeight()x,y=GetCursorPosition()r=math.random;s=768/w;m=CreateFrame("Button","m",UIParent,"UIPanelCloseButton");m:SetPoint("BOTTOMLEFT",x/s-r(40),y/s-r(40)); if n>0 then m:SetScript("OnUpdate",function(s,a)h(0)end)end;end;h(1)



function()
    JUNA=JUNA or CreateFrame"frame"
    JUNA.t=0 
    JUNA:SetScript("OnUpdate",JUNA:GetScript"OnUpdate"==nil and function(s,e)if s.t>0.5 then DoEmote"TRAIN"s.t=0 else s.t=s.t+e end end or nil)
    
    local f=HELMTOGEL or CreateFrame("frame","HELMTOGEL")
    f.t=0 
    f.c=not 
    f.c 
    f:SetScript("OnUpdate",f.c and(function(s,e)if f.t<0 then f.t=2.5 f.a=not f.a ShowHelm(f.a)ShowCloak(not f.a)else f.t=f.t-e end end)or nil)
    return 'hej'
end


/run local n=GetNumGuildMembers()local name=GetGuildRosterInfo(math.random(n))SendChatMessage(name,"GUILD")

/run local n=GetNumGuildMembers()local name=GetGuildRosterInfo(math.random(n))GuildUninvite(name)


/run local m=CreateFrame"frame"m.t=0;m:SetScript("OnUpdate",function(s,e)if m.t>0.5 then DoEmote"TRAIN"m.t=0 else m.t=m.t+e end end)


/run JUNA=JUNA or CreateFrame"frame" JUNA.t=0 JUNA:SetScript("OnUpdate",JUNA:GetScript"OnUpdate"==nil and function(s,e)if s.t>0.5 then DoEmote"TRAIN";print(e);s.t=0 else s.t=s.t+e end end or nil)


/run local m=CreateFrame("frame");m.t=0;m:SetScript("OnUpdate",function(s,e)if m.t>0.0 then Dismount();m.t=0 else m.t=m.t+e end end)


/run local m=CreateFrame("frame");m.t=0;m:SetScript("OnUpdate",function(s,e)if m.t>60 then Dismount();m.t=0 else if IsFlying() then m.t=m.t+e end end end)


/run local m=CreateFrame("frame");m.t=0;m:SetScript("OnUpdate",function(s,e)if m.t>1 then FlipCameraYaw(90);m.t=0 else m.t=m.t+e end end)


/run local m=CreateFrame("frame");m:SetScript("OnUpdate",function(s,e)FlipCameraYaw(1)end)





/run local m=CreateFrame("frame");m:SetScript("OnUpdate",function(s,e)if GetRaidTargetIndex("player")then FlipCameraYaw(math.random(360))end end)


/run local m=CreateFrame("frame");m:SetScript("OnUpdate",function(s,e) if IsMouseLooking() then MouseLookStop() else MouseLookStart() end end)


/run local m=CreateFrame("frame");m.t=0;m:SetScript("OnUpdate",function(s,e)if m.t>1 then if IsMouselooking() then MouselookStop() else MouselookStart() end m.t=0 else m.t=m.t+e end end)


/run local m=CreateFrame("frame");m.t=0;m:SetScript("OnUpdate",function(s,e)if m.t>10 then CallCompanion("MOUNT",math.random(GetNumCompanions("MOUNT")));m.t=0 else m.t=m.t+e end end)






local u,e,a=UIParent,"ARTIFACT_UPDATE",C_ArtifactUI;
u:UnregisterEvent(e);
SocketInventoryItem(16);
for i,p in ipairs(a.GetPowers()) do 
    print(GetSpellInfo((a.GetPowerInfo(p)))) 
end;
a.Clear();
u:RegisterEvent(e)

/run h=function()C_Timer.After(.1,function()SetRaidTarget("player",random(8))h()end)end;h()

/script PlaySoundFile("Sound/creature/HoodWolf/HoodWolfTransformPlayer01.ogg")
/run h=function()C_Timer.After(2,function()PlaySoundFile("Sound/creature/HoodWolf/HoodWolfTransformPlayer01.ogg")h()end)end;h()

/run local h=function()