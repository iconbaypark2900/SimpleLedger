# Personal Finance Assistant With Machine Learning

The Personal Finance Assistant is an intelligent financial management tool that combines traditional accounting features with modern machine learning capabilities to predict future financial trends.

In traditional functionality, it allows users to manage their personal finances by keeping track of transactions. The tool provides options to add deposits to the account, make payments, and view the transaction history. It also includes features for generating various reports such as viewing all payments or deposits, transactions within a specific date range, or even filtering transactions by a specific vendor.

The highlight of the Personal Finance Assistant is the embedded machine learning model, which can make informed predictions about future financial scenarios. This feature utilizes the user's transaction history and spending habits to forecast the year-end balance and the likelihood of overspending in a specific category.

By integrating machine learning algorithms into personal finance, this tool goes beyond simply recording and reporting on transactions. Instead, it provides proactive insights to help users better manage their financial health and make informed decisions about their spending and saving habits.

                     +--------+
                     |  Main  |
                     +---+----+
                         |
   +---------------------+---------------------+
   |                     |                     |
+--v---+             +---v---+             +---v---+
|Ledger|             |Reports|             |LedgerEntry|
+------+             +---+---+             +----------+
                        |
                        |
                    +---v---+
                    |TransactionClassifier|
                    +-------+
