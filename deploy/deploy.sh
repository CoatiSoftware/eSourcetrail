rm updatesite/* -rf
mkdir updatesite

cp -r eSourceTrail.site/target/repository/* updatesite/
cp deploy/index.html updatesite/

# config
git config --global user.email "noreply@coati.io"
git config --global user.name "Travis CI"

git remote add upstream "https://$GH_TOKEN@github.com/$GH_REPO.git"
git fetch upstream
git reset upstream/gh-pages
git add -A updatesite
git commit -m "Deploy p2 Updatesite"
git push -q upstream HEAD:gh-pages


