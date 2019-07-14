import sys

def make_insert_file(csv_file):
    with open(csv_file) as f:
        #This line has the added benefit of singularizing the filename
        #as well, becuase it treats the 's' in '.csv' as a trailing character
        #to be stripped. Would be cool if I had a highly modular singularize()
        #function here, but as is a theme in this project, since we know exactly
        #what our data will look like, our code does not have to be as general
        table_name = csv_file.strip('.csv')        
        columns = f.readline().strip('\n').replace('"', '')
        insert_base = 'INSERT INTO ' + table_name + ' (' + columns + ') VALUES'
        with open('populate_' + table_name + '_table.sql', 'w+') as out:
            for line in f:
                data = line.strip('\n').replace('"', '\'')
                out.write(insert_base + '(' + data + ');' + '\n')


for arg in sys.argv[1:]:
    make_insert_file(arg)
