package com.yupGG.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping(value = "/")
    public String main() {
        return "main";
    }
}

/*


    {
        "leagueId": "64de9d2d-89e0-3698-a7cd-a9b7e04ef3b6",
        "queueType": "RANKED_SOLO_5x5",
        "tier": "MASTER",
        "rank": "I",
        "summonerId": "nLRU4dnS-jAxVerHuJxj_7jQrHrWToRaAGZNwLUsr1iSpw",
        "leaguePoints": 183,
        "wins": 172,
        "losses": 163,
        "veteran": false,
        "inactive": false,
        "freshBlood": false,
        "hotStreak": false
    }
]
*/
