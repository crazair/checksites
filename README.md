checksites
==========
Проверка заданных сайтов с заданной переодичностью:


Входной xml:

  <?xml version="1.0" encoding="windows-1251"?>
  <body>
      <!-- Список сайтов -->
      <sites>
          <site>
              <!-- Адрес сайта -->
              <url>http://test</url>
              <!-- Описание сайта -->
              <description>test</description>
              <!-- Периодичность проверки сайта -->
              <timecheck>3600</timecheck>
              <!-- Проверки сайта. Тип string - поиск текста, тип regular - поиск по регулярному выражению -->
              <patternstest>
                  <pattern type="string">Ошибка обращения к серверу</pattern>
              </patternstest>
              <!-- Список e-mail адресов для рассылки сообщений -->
              <sendadress>
                  <mail>testn@test.com</mail>
                  <mail>test2@test.com</mail>				
              </sendadress>
          </site>	
      </sites>
      <!-- Настройки STMP сервера -->
      <mailsettings>
          <!-- STMP сервер -->
          <host>postman.test.com</host>
          <!-- Имя отображаемое в рассылке -->
          <sendername>CheckSites</sendername>
      </mailsettings>
  </body>
