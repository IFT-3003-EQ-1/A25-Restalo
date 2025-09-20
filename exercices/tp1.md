# Exercices - TP1

## Conventions Git
Voici les conventions et stratégies d'utilisations de Git que nous avons décidé d'adopter.

### Nommage et convention concernant les commits
Le commit est le bloc atomic lorsque nous travaillons avec Git. Ainsi, il doit être simple et bref à décrire. 

1) Le titre du commit doit commencer par un verbe a l'infinitif (fix, ajouter, modifier, supprimer, etc.)
2) Le reste du titre doit simplement décrire l'objet ciblé par l'action.
3) Le corps est faculatif. Il doit être utilisé pour ajouter du context au titre. Il ne devrait jamais faire plus de 50 characters. Dans le cas contraire, le commit doit être subdivisé.

Note : le code dans un commit n'a pas besoin d'être correct, ou même de compiler. C'est une étape atomic dans le workflow. L'important, c'est de commit régulièrement. 

Référence : https://cbea.ms/git-commit/#separate

### Stratégie de branchage

1) Une branche par feature. Un developer peut posséder plusieurs branches pour différents features. Cependant, chaque branche est la responsabilité d'un et un seul développeur.
2) Quand un feature est terminé, le developper doit soumettre sont travail sous la forme d'un pull request vers Master. Chaque pull request doit être validé par un autre développeur avant d'être merged.
3) Le Master contient la version stable la plus récente de l'application. Il est donc impératif que le travail mergé dans le Master soit systématiquement révisé et testé.
4) Lorsque l'équipe termine un milestone, une branche ayant l'étiquette correspondante sera créé à partir du Master. Ces branches representes les versions de l'application en production.


Référence : https://githubflow.github.io/

### Utilisation de l'IA

Nous avons utilisé l'outil de synthèse de Google (AI overview) pour simplifier nos recherches web.
