package com.github.iiscoolso123.mcmonster.utils;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.world.WorldSettings.GameType;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TabListUtils {

    private static final Pattern visitorPattern = Pattern.compile("Visitors: \\((\\d)\\)");
    private static final Pattern nextVisitorPattern = Pattern.compile("^Next Visitor: (.+)$");
    private static final Pattern areaPattern = Pattern.compile("Area: (.+)");
    private static final Pattern mithrilPattern = Pattern.compile("Mithril: (.+)");

    public static String area = "";
    public static int mithril = 0;
    public static boolean explosivity = false;
    public static boolean maxVisitors = false;
    public static boolean emptyComposter = false;
    public static String gardenMilestone = "";
    public static String timeTillNextVisitor = "";
    public static int numVisitors = -1;
    public static String archerName = "";

    private static final Ordering<NetworkPlayerInfo> playerInfoOrdering = new Ordering<NetworkPlayerInfo>() {
        @Override
        public int compare(NetworkPlayerInfo info1, NetworkPlayerInfo info2) {
            if (info1 == null) return -1;
            if (info2 == null) return 0;
            return ComparisonChain.start()
                .compareTrueFirst(!isSpectator(info1.getGameType()), !isSpectator(info2.getGameType()))
                .compare(Objects.requireNonNull(info1.getPlayerTeam() == null ? "" : info1.getPlayerTeam().getRegisteredName(), ""),
                         Objects.requireNonNull(info2.getPlayerTeam() == null ? "" : info2.getPlayerTeam().getRegisteredName(), ""))
                .compare(info1.getGameProfile().getName(), info2.getGameProfile().getName())
                .result();
        }
    };

    public static List<NetworkPlayerInfo> fetchTabEntries() {
        if (Minecraft.getMinecraft().thePlayer == null) {
            return Collections.emptyList();
        } else {
            return playerInfoOrdering.sortedCopy(Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfoMap());
        }
    }

    /**
     * Sets a bunch of useful values based on the state of the scoreboard. Functionality is collected all into
     * this one method in order to avoid more transversal of the list than is necessary, as these checks need
     * to happen somewhat frequently.
     */
    public static void parseTabEntries() {
        boolean exploFlag = false;
        boolean numVisitorsFlag = false;

        List<String> scoreboardList = fetchTabEntries().stream()
            .map(info -> info.getDisplayName() != null ? info.getDisplayName().getUnformattedText() : null)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        for (String line : scoreboardList) {
            String trimmed = line.trim();
            Matcher areaMatcher = areaPattern.matcher(trimmed);
            Matcher mithrilMatcher = mithrilPattern.matcher(trimmed);
            Matcher visitorMatcher = visitorPattern.matcher(trimmed);
            Matcher nextVisitorMatcher = nextVisitorPattern.matcher(trimmed);

            if (mithrilMatcher.find()) {
                NumberFormat nf = NumberFormat.getInstance();
                try {
                    mithril = nf.parse(mithrilMatcher.group(1)).intValue();
                }catch (ParseException e){
                    e.printStackTrace();
                }

            }

            if (areaMatcher.find()) {
                area = areaMatcher.group(1);
            } else if ("Volcano Explosivity:".equals(trimmed)) {
                exploFlag = true;
            } else if (exploFlag) {
                exploFlag = false;
                explosivity = !"INACTIVE".equals(trimmed);
            } else if ("Dungeon Stats".equals(trimmed)) {
                area = "Dungeon";
            } else if (trimmed.startsWith("Time Left: ")) {
                emptyComposter = "INACTIVE".equals(trimmed.split(": ")[1]);
            } else if ("Crop Milestones:".equals(trimmed)) {
                int index = scoreboardList.indexOf(line) + 1;
                if (index < scoreboardList.size()) {
                    gardenMilestone = scoreboardList.get(index).trim();
                }
            } else if (visitorMatcher.find()) {
                numVisitors = Integer.parseInt(visitorMatcher.group(1));
                numVisitorsFlag = true;
            } else if (nextVisitorMatcher.find()) {
                timeTillNextVisitor = nextVisitorMatcher.group(1);
                maxVisitors = "Queue Full!".equals(timeTillNextVisitor);
            } else if (line.contains("(Archer")) {
                archerName = line.split(" ")[1];
            }
        }

        if (!"Dungeon".equals(area)) {
            archerName = "";
        }
        if (!"Crimson Isle".equals(area)) {
            explosivity = false;
        }
        if (!"Garden".equals(area)) {
            maxVisitors = false;
        }
        if (!numVisitorsFlag) {
            numVisitors = -1;
        }
    }

    private static boolean isSpectator(GameType gameType) {
        return gameType == GameType.SPECTATOR;
    }
}
