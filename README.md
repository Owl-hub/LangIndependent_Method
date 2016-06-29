# LangIndependent_Method

Класс Aspect — класс-сущность, содержит в себе строку, являющуюся кандидатом в характеристики и тег, обозначающий его часть речи. Список строк используется для характеристик, представленных словосочетаниями.

Класс PreprocessedReview — класс-сущность, содержит в себе отзыв, разбитый на слова. Каждое слово помечено тегом, обозначающим часть речи.

Класс Corpus — класс-сущность, содержит список предобработанных отзывов и исходный список отзывов.

Класс Data — класс-сущность, содержит список верных характеристик и список стоп-слов.

Класс Pair — вспомогательный класс для работы с парами объектов.

Интерфейс IAspectExtractor содержит метод extract, который получает на вход коллекцию отзывов, на выходе возвращает список кандидатов в характеристики.

Класс AspectExtractor реализует интерфейс IAspectExtractor, является основным классом системы. В его методе extract происходит запрос ко всем остальным методам системы.

Интерфейс IReviewPreprocessor содержит метод process, который получает на вход отзыв, на выходе возвращает предобработанный отзыв.

Класс TexterraBased реализует интерфейс IReviewPreprocessor, отвечает за предобработку отзывов. В нем происходит морфологическая разметка текста. Отзывы разбиваются на предложения, происходит определение частей речи. Также в этом классе происходит лемматизация.

Интерфейс IAspectCandidateExtractor содержит метод extract, который получает на вход коллекцию предобработанных отзывов, на выходе возвращает множество кандидатов в характеристики.

Класс NounExtractor реализует интерфейс IAspectCandidateExtractor, отвечает за фильтрацию слов с использованием морфологической разметки. Содержит метод extract, который анализирует части речи и возвращает список слов, состоящий только из существительных.

Класс NPhrasesExtractorреализует интерфейс IAspectCandidateExtractor. Содержит метод extract, который анализирует части речи и возвращает список слов, состоящий только из словосочетаний (именных фраз).

Интерфейс IAspectCandidateWeighter содержит метод weight, который получает на вход коллекцию предобработанных отзывов и множество полученных на предыдущем этапе кандидатов в характеристики, на выходе возвращает список пар кандидатов в характеристики и их весов.

Класс Cvalue реализует интерфейс IAspectCandidateWeighter, отвечает за вычисление для кандидатов в характеристики метрики C-value.

Интерфейс IUnsupervisedMethod содержит метод run, который получает на вход обучающее множество и список всех кандидатов в характеристики, на выходе выдает измененный список кандидатов в характеристики.

Класс Bootstrapping реализует интерфейс IUnsupervisedMethod, отвечает за реализацию алгоритма загрузки.

Интерфейс IAspectCandidateFilter содержит метод filter, который получает список, состоящий из кандидатов в характеристики, и возвращает его в отфильтрованном виде.

Класс PrunningFilter реализует интерфейс IAspectCandidateFilter, отвечает за фильтрацию слов-кандидатов в характеристики с помощью составленного списка стоп-слов.

Класс Efficiency отвечает за вычисление эффективности работы метода.
