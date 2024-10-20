INSERT INTO manufacturers (manufacturer_name, manufacturer_address)
VALUES 
('Apple Inc.', '1 Apple Park Way, Cupertino, CA 95014'),
('Samsung Electronics Co., Ltd.', '129 Samsung-ro, Maetan-dong, Yeongtong-gu, Suwon-si, Gyeonggi-do, South Korea'),
('Sony Corporation', '1-7-1 Konan, Minato-ku, Tokyo 108-0075, Japan'),
('Microsoft Corporation', 'One Microsoft Way, Redmond, WA 98052-6399'),
('LG Electronics Inc.', '128 Yeoui-daero, Yeongdeungpo-gu, Seoul, South Korea'),
('Dell Technologies Inc.', '1 Dell Way, Round Rock, TX 78682'),
('Lenovo Group Limited', 'Lincoln House, 137-143 Hammersmith Road, London W14 0QL, United Kingdom'),
('HP Inc.', '1501 Page Mill Road, Palo Alto, CA 94304'),
('ASUS Computer International', '800 Corporate Way, Fremont, CA 94539'),
('Acer Inc.', 'No. 88, Section 1, Xintai 5th Road, Xizhi, New Taipei City 221, Taiwan'),
('Canon Inc.', '30-2, Shimomaruko 3-chome, Ohta-ku, Tokyo 146-8501, Japan'),
('Nikon Corporation', 'Shinagawa Intercity Tower C, 2-15-3, Konan, Minato-ku, Tokyo 108-6290, Japan'),
('Panasonic Corporation', '1006, Oaza Kadoma, Kadoma-shi, Osaka 571-8501, Japan'),
('Sharp Corporation', '1 Takumi-cho, Sakai-ku, Sakai City, Osaka 590-8522, Japan'),
('Toshiba Corporation', '1-1, Shibaura 1-chome, Minato-ku, Tokyo 105-8001, Japan'),
('Fujitsu Limited', 'Shiodome City Center, 1-5-2 Higashi-Shimbashi, Minato-ku, Tokyo 105-7123, Japan'),
('Hitachi, Ltd.', '6-6, Marunouchi 1-chome, Chiyoda-ku, Tokyo 100-8280, Japan'),
('IBM Corporation', '1 New Orchard Road, Armonk, New York 10504-1722'),
('Oracle Corporation', '500 Oracle Parkway, Redwood Shores, CA 94065'),
('Google LLC', '1600 Amphitheatre Parkway, Mountain View, CA 94043'),
('Facebook, Inc.', '1 Hacker Way, Menlo Park, CA 94025'),
('Twitter, Inc.', '1355 Market Street, Suite 900, San Francisco, CA 94103'),
('LinkedIn Corporation', '1000 W. Maude Avenue, Sunnyvale, CA 94085'),
('Amazon.com, Inc.', '410 Terry Avenue North, Seattle, WA 98109'),
('eBay Inc.', '2025 Hamilton Avenue, San Jose, CA 95125'),
('Alibaba Group Holding Limited', '699 Wang Shang Road, Binjiang District, Hangzhou, Zhejiang, China'),
('Tencent Holdings Limited', 'Tencent Building, Kejizhongyi Avenue, Hi-tech Park, Nanshan District, Shenzhen, China'),
('Baidu, Inc.', 'Baidu Campus, No. 10, Shangdi 10th Street, Haidian District, Beijing, China'),
('JD.com, Inc.', 'Building A, No. 18 Kechuang 11th Street, BDA, Beijing, China'),
('TikTok Inc.', '1 Hacker Way, Menlo Park, CA 94025'),
('Snap Inc.', '2772 Donald Douglas Loop North, Santa Monica, CA 90405'),
('Pinterest, Inc.', '505 Brannan Street, San Francisco, CA 94107'),
('Reddit, Inc.', '520 3rd Street, San Francisco, CA 94107'),
('Netflix, Inc.', '100 Winchester Circle, Los Gatos, CA 95032'),
('Hulu, LLC', '2500 Broadway, Santa Monica, CA 90404'),
('Spotify Technology S.A.', '42-44 Avenue de la Gare, L-1610 Luxembourg'),
('Pandora Media, LLC', '2100 Franklin Street, Oakland, CA 94612'),
('SoundCloud Limited', '33 St. James Square, London SW1Y 4JS, United Kingdom'),
('Twitch Interactive, Inc.', '225 Bush Street, San Francisco, CA 94104'),
('Epic Games, Inc.', '620 Crossroads Blvd, Cary, NC 27518'),
('Unity Technologies', '30 3rd Street, San Francisco, CA 94103'),
('Activision Blizzard, Inc.', '3100 Ocean Park Blvd, Santa Monica, CA 90405'),
('Electronic Arts Inc.', '209 Redwood Shores Pkwy, Redwood City, CA 94065'),
('Take-Two Interactive Software, Inc.', '110 W 44th St, New York, NY 10036'),
('Ubisoft Entertainment SA', '28 Rue Armand Carrel, 93100 Montreuil, France'),
('Square Enix Holdings Co., Ltd.', 'Shinjuku Bunka Quint Bldg., 3-22-7 Yoyogi, Shibuya-ku, Tokyo 151-8648, Japan'),
('Capcom Co., Ltd.', '3-1-3 Uchihirano-machi, Chuo-ku, Osaka 540-0037, Japan'),
('Konami Holdings Corporation', '9-7-2 Akasaka, Minato-ku, Tokyo 107-8324, Japan'),
('Bandai Namco Holdings Inc.', '5-37-8 Shiba, Minato-ku, Tokyo 108-0014, Japan'),
('Sega Sammy Holdings Inc.', '1-1-1 Haneda, Ota-ku, Tokyo 144-8531, Japan'),
('Nintendo Co., Ltd.', '11-1 Kamitoba-hokotate-cho, Minami-ku, Kyoto 601-8501, Japan'),
('Sony Interactive Entertainment LLC', '2207 Bridgepointe Pkwy, San Mateo, CA 94404'),
('Microsoft Studios', 'One Microsoft Way, Redmond, WA 98052-6399'),
('Valve Corporation', '10400 NE 4th St, Bellevue, WA 98004'),
('Blizzard Entertainment, Inc.', '1 Blizzard Way, Irvine, CA 92618'),
('Epic Games, Inc.', '620 Crossroads Blvd, Cary, NC 27518'),
('Rockstar Games, Inc.', '622 Broadway, New York, NY 10012'),
('Square Enix Co., Ltd.', 'Shinjuku Bunka Quint Bldg., 3-22-7 Yoyogi, Shibuya-ku, Tokyo 151-8648, Japan');

-- Insert manufacturer email addresses
INSERT INTO manufacturer_email_addresses (manufacturer_id, email_address)
VALUES 
(1, 'contact@apple.com'),
(2, 'support@samsung.com'),
(3, 'info@sony.com'),
(4, 'contact@microsoft.com'),
(5, 'support@lg.com'),
(6, 'support@dell.com'),
(7, 'info@lenovo.com'),
(8, 'contact@hp.com'),
(9, 'info@asus.com'),
(10, 'support@acer.com'),
(11, 'info@canon.com'),
(12, 'contact@nikon.com'),
(13, 'support@panasonic.com'),
(14, 'info@sharp.com'),
(15, 'contact@toshiba.com'),
(16, 'info@fujitsu.com'),
(17, 'support@hitachi.com'),
(18, 'contact@ibm.com'),
(19, 'support@oracle.com'),
(20, 'contact@google.com'),
(21, 'support@facebook.com'),
(22, 'contact@twitter.com'),
(23, 'info@linkedin.com'),
(24, 'support@amazon.com'),
(25, 'contact@ebay.com'),
(26, 'support@alibaba.com'),
(27, 'info@tencent.com'),
(28, 'support@baidu.com'),
(29, 'contact@jd.com'),
(30, 'support@tiktok.com'),
(31, 'info@snap.com'),
(32, 'support@pinterest.com'),
(33, 'contact@reddit.com'),
(34, 'support@netflix.com'),
(35, 'info@hulu.com'),
(36, 'support@spotify.com'),
(37, 'info@pandora.com'),
(38, 'contact@soundcloud.com'),
(39, 'support@twitch.com'),
(40, 'info@epicgames.com'),
(41, 'contact@unity.com'),
(42, 'support@activision.com'),
(43, 'contact@ea.com'),
(44, 'info@taketwo.com'),
(45, 'support@ubisoft.com'),
(46, 'info@square-enix.com'),
(47, 'support@capcom.com'),
(48, 'contact@konami.com'),
(49, 'info@bandainamco.com'),
(50, 'support@sega.com'),
(51, 'contact@nintendo.com'),
(52, 'info@sonyinteractive.com'),
(53, 'contact@microsoft.com'),
(54, 'support@valve.com'),
(55, 'contact@blizzard.com'),
(56, 'info@epicgames.com'),
(57, 'contact@rockstargames.com'),
(58, 'support@square-enix.com');



INSERT INTO categories (category_name)
VALUES 
('Electronics'),
('Furniture'),
('Clothing'),
('Books'),
('Groceries'),
('Toys'),
('Sports Equipment'),
('Beauty Products'),
('Office Supplies'),
('Automotive'),
('Kitchen Appliances'),
('Gardening Tools'),
('Fitness Gear'),
('Pet Supplies'),
('Home Decor');


INSERT INTO products (product_name, expiry_date, stock_quantity, cost_price, maximum_retail_price, category_id)
VALUES
('iPhone 15 Pro', '2025-12-31', 500, 999.99, 1299.99, 1),
('Galaxy S23 Ultra', '2025-12-31', 700, 899.99, 1199.99, 1),
('Sony WH-1000XM5', '2026-01-31', 300, 299.99, 349.99, 1),
('Surface Pro 9', '2025-12-31', 400, 799.99, 999.99, 1),
('LG OLED TV 55"', '2026-05-15', 200, 1199.99, 1499.99, 1),
('Dell XPS 13', '2025-11-30', 350, 999.99, 1249.99, 1),
('Lenovo ThinkPad X1', '2026-04-30', 250, 1199.99, 1499.99, 1),
('HP Spectre x360', '2025-11-30', 300, 1099.99, 1299.99, 1),
('ASUS ROG Strix', '2026-02-28', 400, 1499.99, 1799.99, 1),
('Acer Predator Helios 300', '2026-03-31', 350, 1299.99, 1599.99, 1),
('Canon EOS R5', '2026-06-30', 150, 3899.99, 4499.99, 1),
('Nikon Z7 II', '2026-05-31', 150, 3299.99, 3799.99, 1),
('Panasonic Lumix S5', '2026-07-31', 200, 1999.99, 2499.99, 1),
('Sharp 8K TV 65"', '2026-05-15', 100, 4999.99, 5999.99, 1),
('Toshiba Satellite Pro', '2025-10-31', 300, 799.99, 999.99, 1),
('Fujitsu Lifebook U7511', '2026-04-30', 250, 1099.99, 1299.99, 1),
('Hitachi 4TB Hard Drive', '2027-02-28', 500, 99.99, 149.99, 1),
('IBM ThinkSystem SR650', '2026-12-31', 100, 4999.99, 5999.99, 1),
('Oracle Cloud Server', '2026-09-30', 80, 6999.99, 7999.99, 9),
('Google Pixel 7', '2025-12-31', 700, 599.99, 799.99, 1),
('Facebook Oculus Quest 3', '2026-01-31', 300, 399.99, 499.99, 1),
('Twitter Blue Check', '2025-12-31', 1000, 7.99, 9.99, 9),
('LinkedIn Premium', '2025-12-31', 500, 29.99, 39.99, 9),
('Amazon Echo Dot', '2026-01-15', 600, 29.99, 49.99, 9),
('eBay Gift Card $100', '2025-12-31', 1000, 100.00, 110.00, 9),
('Alibaba Cloud Storage', '2026-07-31', 800, 999.99, 1199.99, 9),
('Tencent WeChat Pay', '2025-12-31', 1000, 0.00, 0.00, 9),
('Baidu AI Translator', '2026-08-31', 200, 199.99, 249.99, 9),
('JD.com Warehouse Robot', '2026-09-30', 50, 9999.99, 11999.99, 1),
('TikTok Creator Kit', '2026-02-28', 400, 149.99, 199.99, 9),
('Snap Spectacles 3', '2026-01-31', 300, 299.99, 349.99, 1),
('Pinterest Marketing Pack', '2025-12-31', 500, 19.99, 29.99, 9),
('Reddit Premium', '2025-12-31', 600, 5.99, 7.99, 9),
('Netflix 1-Year Subscription', '2026-01-01', 1000, 119.99, 139.99, 9),
('Hulu Live TV', '2026-01-31', 700, 69.99, 79.99, 9),
('Spotify Family Plan', '2025-12-31', 800, 14.99, 19.99, 9),
('Pandora Plus', '2025-12-31', 600, 4.99, 6.99, 9),
('SoundCloud Pro Unlimited', '2025-12-31', 500, 15.00, 20.00, 9),
('Twitch Turbo', '2025-12-31', 1000, 8.99, 10.99, 9),
('Epic Games Fortnite Pack', '2026-12-31', 500, 19.99, 24.99, 9);

-- Link products to manufacturers
INSERT INTO product_manufacturers (product_id, manufacturer_id)
VALUES
(1, 1),  -- iPhone 15 Pro by Apple
(2, 2),  -- Galaxy S23 Ultra by Samsung
(3, 3),  -- Sony WH-1000XM5 by Sony
(4, 4),  -- Surface Pro 9 by Microsoft
(5, 5),  -- LG OLED TV by LG
(6, 6),  -- Dell XPS 13 by Dell
(7, 7),  -- Lenovo ThinkPad X1 by Lenovo
(8, 8),  -- HP Spectre x360 by HP
(9, 9),  -- ASUS ROG Strix by ASUS
(10, 10), -- Acer Predator Helios 300 by Acer
(11, 11), -- Canon EOS R5 by Canon
(12, 12), -- Nikon Z7 II by Nikon
(13, 13), -- Panasonic Lumix S5 by Panasonic
(14, 14), -- Sharp 8K TV by Sharp
(15, 15), -- Toshiba Satellite Pro by Toshiba
(16, 16), -- Fujitsu Lifebook U7511 by Fujitsu
(17, 17), -- Hitachi 4TB Hard Drive by Hitachi
(18, 18), -- IBM ThinkSystem by IBM
(19, 19), -- Oracle Cloud Server by Oracle
(20, 20), -- Google Pixel 7 by Google
(21, 21), -- Oculus Quest 3 by Facebook
(22, 22), -- Twitter Blue Check by Twitter
(23, 23), -- LinkedIn Premium by LinkedIn
(24, 24), -- Amazon Echo Dot by Amazon
(25, 25), -- eBay Gift Card by eBay
(26, 26), -- Alibaba Cloud Storage by Alibaba
(27, 27), -- Tencent WeChat Pay by Tencent
(28, 28), -- Baidu AI Translator by Baidu
(29, 29), -- JD.com Warehouse Robot by JD.com
(30, 30), -- TikTok Creator Kit by TikTok
(31, 31), -- Snap Spectacles 3 by Snap
(32, 32), -- Pinterest Marketing Pack by Pinterest
(33, 33), -- Reddit Premium by Reddit
(34, 34), -- Netflix Subscription by Netflix
(35, 35), -- Hulu Live TV by Hulu
(36, 36), -- Spotify Family Plan by Spotify
(37, 37), -- Pandora Plus by Pandora
(38, 38), -- SoundCloud Pro by SoundCloud
(39, 39), -- Twitch Turbo by Twitch
(40, 40); -- Fortnite Pack by Epic Games
